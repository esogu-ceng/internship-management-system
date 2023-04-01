package tr.edu.ogu.ceng.utilities.mappers;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

	ModelMapper forResponse();
	ModelMapper forRequest();

}
