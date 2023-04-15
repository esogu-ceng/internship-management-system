package tr.edu.ogu.ceng.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {
	public static Pageable createPageRequest(Integer pageNo, Integer limit, String sortBy) {
		Sort sort = Sort.by(sortBy);
		return PageRequest.of(pageNo, limit, sort);
	}
}
