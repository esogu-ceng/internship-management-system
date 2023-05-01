package tr.edu.ogu.ceng.advice;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ImsAOPExample {

	// The execution of any method defined in the service package
	@Around("execution(* tr.edu.ogu.ceng.service.*.*(..))")
	public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.nanoTime();
		try {
			Object retval = pjp.proceed();
			long end = System.nanoTime();
			String methodName = pjp.getSignature().getName();
			log.info("Execution of " + methodName + " took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
			return retval;
		} catch (Exception e) {
			long end = System.nanoTime();
			String methodName = pjp.getSignature().getName();
			log.info("Exceptional execution of " + methodName + " took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
			throw e;
		}
	}

}
