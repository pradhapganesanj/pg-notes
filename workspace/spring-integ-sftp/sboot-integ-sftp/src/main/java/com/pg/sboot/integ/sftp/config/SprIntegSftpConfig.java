package com.pg.sboot.integ.sftp.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
public class SprIntegSftpConfig {
	
	@Bean
	public SessionFactory<LsEntry> sftpSessionFactory(){
		DefaultSftpSessionFactory sessFact = new DefaultSftpSessionFactory(true);
		sessFact.setHost("localhost");
		sessFact.setPort(9876);
		sessFact.setUser("pg");
		sessFact.setPassword("pg");
		sessFact.setAllowUnknownKeys(true);
		return new CachingSessionFactory<>(sessFact);
	}
	@Bean
	public SftpInboundFileSynchronizer sftpInboundFileSynchronizer() {
        SftpInboundFileSynchronizer fileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory());
        fileSynchronizer.setDeleteRemoteFiles(false);
        fileSynchronizer.setRemoteDirectory("C:\\sftp\\out");
        fileSynchronizer
                .setFilter(new SftpSimplePatternFileListFilter("*.txt"));
        return fileSynchronizer;
    }
	
    @Bean
    @InboundChannelAdapter(channel = "sftpChannel", poller = @Poller(cron = "0/10 * * * * *"))
    public MessageSource<File> sftpMessageSource() {
        SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(
                sftpInboundFileSynchronizer());
        source.setLocalDirectory(new File("C:\\sftp\\in"));
        source.setAutoCreateLocalDirectory(true);
        source.setLocalFilter(new AcceptOnceFileListFilter<File>());
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = "sftpChannel")
    public MessageHandler resultFileHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.err.println(message.getPayload());
            }
        };
    }

}
