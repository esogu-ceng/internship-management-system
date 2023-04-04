package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException{
	
	public EntityNotFoundException(String message){
		super(message);
	}
	
	public EntityNotFoundException(){
		super("Not found.");
	}
}
