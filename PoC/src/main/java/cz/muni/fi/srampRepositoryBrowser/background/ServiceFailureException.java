package cz.muni.fi.srampRepositoryBrowser.background;

/**
 * service failture exception
 * @author Jan Bouska
 *
 */
public class ServiceFailureException extends RuntimeException {


	

	public ServiceFailureException(String msg) {
        super(msg);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}