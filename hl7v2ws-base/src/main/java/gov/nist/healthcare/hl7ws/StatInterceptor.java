package gov.nist.healthcare.hl7ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.MDC;

public class StatInterceptor  extends AbstractLoggingInterceptor {

	private final String SESSION_KEY = "sessionID";
	private final String REQUEST_URL = "url";
	private final String HOST = "host";
	private final String SIZE = "size";
	private final String STATUS = "status";

    
    public StatInterceptor() {
        super(Phase.RECEIVE);
    }

    public void handleMessage(Message message) throws Fault {
         	logStat(message);
     }

    protected void logStat(Message message) throws Fault {
        
    	HttpServletRequest req = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
     	
    	MDC.put(SESSION_KEY, req.getSession().getId());
		MDC.put(HOST, getIpAddress(req));
		MDC.put(REQUEST_URL, getUrl(req));
		MDC.put(SIZE, getSize(req));
 		MDC.put(STATUS, "-1");
    	
    }
    
    
    
  


	/**
	 * return the client IP address
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null)
			ipAddress = request.getRemoteAddr();

		return ipAddress;
	}

	/**
	 * return the reuqest url
	 * 
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request) {
		return request.getMethod() + " " + request.getContextPath() + "/"
				+ request.getProtocol();
	}

	/**
	 * return the requested resource size
	 * 
	 * @param request
	 * @return
	 */
	private String getSize(HttpServletRequest request) {
		return request.getHeader("Content-Length") == null ? "" : request
				.getHeader("Content-Length");
	}
}