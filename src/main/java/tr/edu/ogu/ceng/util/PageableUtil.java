package tr.edu.ogu.ceng.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {
	public static Pageable createPageRequest(Integer pageNo, Integer pageSize, String sortBy) {
		Sort sort = Sort.by(sortBy);
		return PageRequest.of(pageNo, pageSize, sort);
	}
}
