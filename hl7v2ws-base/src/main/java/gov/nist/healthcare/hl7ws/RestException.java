package gov.nist.healthcare.hl7ws;

public class RestException extends RuntimeException {

	public RestException() {
		super();
	}

	public RestException(String error) {
		super(error);
	}

}
