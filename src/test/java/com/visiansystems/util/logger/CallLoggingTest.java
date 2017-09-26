package com.visiansystems.util.logger;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
public class CallLoggingTest {

    @Autowired
    private CallLoggingFoo callLoggingFoo;

    @Test
    @Ignore
    public void callLogTest() {

        Logger rootLogger = Logger.getRootLogger();
        Appender mockAppender = Mockito.mock(Appender.class);
        when(mockAppender.getName()).thenReturn("MockAppender");
        rootLogger.addAppender(mockAppender);

        callLoggingFoo.bar("hello", 123);

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher<LoggingEvent>() {
            @Override
            public boolean matches(Object argument) {
                final LoggingEvent event = (LoggingEvent)argument;
                final String message = (String)event.getMessage();

                return message.equals("Entering method bar[hello, 123]");
            }
        }));

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher<LoggingEvent>() {
            @Override
            public boolean matches(Object argument) {
                final LoggingEvent event = (LoggingEvent)argument;
                final String message = (String)event.getMessage();

                return message.equals("Exiting method bar[void]");
            }
        }));
    }
}

@Component class CallLoggingFoo {
    @CallLogging(CallLogging.Level.DEBUG)
    @ReturnLogging(ReturnLogging.Level.DEBUG)
    public void bar(String text, int integer) {
    }
}
