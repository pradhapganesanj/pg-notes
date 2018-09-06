package com.pg.sboot.batch.csvora.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
//import org.springframework.batch.item.MarkFailedException;
//import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
//import org.springframework.batch.item.ResetFailedException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

public class ExcelItemReader extends FlatFileItemReader {

	private Resource resource;
	private Integer sheetNum = 0;
	private Integer headLineRow = 0;
	private Boolean isCopied = false;
	// private String tmpFile;
	private Map<Integer, String> columnMap = new HashMap<Integer, String>();
	private Map<String, Integer> headerMap = new HashMap<String, Integer>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private Sheet sheet;
	private Integer lineCount = 0;
	private Integer totalRows = 0;

	private static final Log logger = LogFactory.getLog(ExcelItemReader.class);

	public void setHeader(String name) {
		logger.info("process header..." + name);
		String[] names = name.split(",");
		for (int i = 0; i < names.length; i++) {
			columnMap.put(i, names[i]);
			headerMap.put(names[i], i);
		}
	}

	public void setHeadLineRow(Integer headLineRow) {
		this.headLineRow = headLineRow;
	}

	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("afterPropertiesSet...");
	}

	/*
	 * public void mark() throws MarkFailedException { // TODO Auto-generated
	 * method stub logger.info("nark..."); }
	 */

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("load line:" + lineCount);
		if (lineCount >= totalRows) {
			logger.info("read over all rows!");
			return null;
		}
		Map<String, String> dataMap = new HashMap<String, String>();
		Row dataRow = sheet.getRow(lineCount);
		for (int i = dataRow.getFirstCellNum(); i < dataRow.getLastCellNum(); i++) {
			Cell cell = dataRow.getCell(i);
			String headerName = columnMap.get(i);
			String dataValue = "";
			if (cell == null) {
				logger.info("cell is null:" + i);
				break;
			}

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					dataValue = dateFormat.format(date);
				}
				dataValue = cell.getNumericCellValue() + "";
				break;
			default:
				dataValue = cell.getStringCellValue();
			}

			dataMap.put(headerName, dataValue);
		}
		if (isEmpty(dataMap)) {
			logger.info("empty row:" + lineCount);
			return null;
		}
		lineCount++;
		return dataMap;
	}

	private boolean isEmpty(Map<String, String> dataMap) {
		if (dataMap == null)
			return true;
		boolean isEmpty = true;
		for (String key : headerMap.keySet()) {
			if (dataMap.get(key) != null && !dataMap.get(key).trim().equals("")) {
				isEmpty = false;
			}
		}
		return isEmpty;
	}

	/*
	 * @Override public void reset() throws ResetFailedException { // TODO
	 * Auto-generated method stub logger.info("reset..."); }
	 */

	/*
	 * @Override public void close(ExecutionContext executioncontext) throws
	 * ItemStreamException { try { resource.getInputStream().close(); } catch
	 * (IOException e) { logger.error("close failed!",e); throw new
	 * ItemStreamException(e); } }
	 */

	@Override
	public void close() throws ItemStreamException {
		try {
			resource.getInputStream().close();
		} catch (IOException e) {
			logger.error("close failed!", e);
			throw new ItemStreamException(e);
		}
	}

	@Override
	public void open(ExecutionContext executioncontext) throws ItemStreamException {
		logger.info("open Excel...");
		String fileName = resource.getFilename();
		Workbook workbook = null;
		try {
			InputStream inputStream = resource.getInputStream();
			if (isCopied) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int b = 0;
				while ((b = inputStream.read()) != -1) {
					bos.write(b);
				}
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				inputStream = bis;
			}

			if (fileName.toUpperCase().indexOf(".XLSX") != -1) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}
			sheet = workbook.getSheetAt(sheetNum);
			if (sheet == null) {
				throw new ItemStreamException("Can't get the specified sheet!");
			}
			Row header = sheet.getRow(headLineRow);
			if (header != null) {
				for (int i = header.getFirstCellNum(); i < header.getLastCellNum(); i++) {
					Cell cell = header.getCell(i);
					String cellValue = cell.getStringCellValue();
					// columnMap.put(i, cellValue);
					// headerMap.put(cellValue, i);
					logger.info("header name:" + cellValue);
				}
			}
			lineCount = headLineRow + 1;

			int lastRowNum = sheet.getLastRowNum();
			int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
			totalRows = physicalNumberOfRows < lastRowNum ? physicalNumberOfRows : lastRowNum;

			logger.info("lastRowNum:" + lastRowNum);
			logger.info("physicalNumberOfRows:" + physicalNumberOfRows);
			logger.info("total rows:" + totalRows);

		} catch (IOException e) {
			logger.error("open excel fail!", e);
			throw new ItemStreamException(e);
		}

	}

	@Override
	public void update(ExecutionContext executioncontext) throws ItemStreamException {
		// TODO Auto-generated method stub
		logger.info("update...");
	}

}