package bd;

public class BDExcecaoIntegridade extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BDExcecaoIntegridade (String msg) {
		super(msg);
	}
}
