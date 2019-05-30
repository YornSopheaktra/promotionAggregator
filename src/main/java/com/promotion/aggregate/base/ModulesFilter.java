package com.promotion.aggregate.base;

import java.util.Map;

import com.tmc.frmk.core.domain.request.RequestDTO;
import org.springframework.context.ApplicationContext;

public abstract class ModulesFilter<Q, R,S> {

	public abstract R process(RequestDTO request, R res);

	protected final BaseProcess<Q, R,S> getProcessor(ApplicationContext context,String module) {
		BaseProcess<Q, R,S> processor = null;
		Map<String, Object> beans = context.getBeansWithAnnotation(Modules.class);
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			if (entry.getValue() instanceof BaseProcess|| entry.getValue().getClass().isAssignableFrom(BaseProcess.class)) {
				Modules annotation = context.findAnnotationOnBean(entry.getKey(), Modules.class);
				String beanModule = annotation.value();
				System.out.println("===========beanServiceId: "+beanModule+ " ###serviceId: "+ module);
				if (beanModule != null && module !=null) {
					if (beanModule.equals(module))
						return (BaseProcess<Q, R,S>) entry.getValue();
				}
			}
		}

		return processor;
	}
}
