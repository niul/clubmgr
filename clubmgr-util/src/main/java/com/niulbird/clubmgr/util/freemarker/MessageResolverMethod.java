package com.niulbird.clubmgr.util.freemarker;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class MessageResolverMethod implements TemplateMethodModelEx {

	private MessageSource messageSource;
	private Locale locale;

	public MessageResolverMethod(MessageSource messageSource, Locale locale) {
		this.messageSource = messageSource;
		this.locale = locale;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() != 1) {
			throw new TemplateModelException("Wrong number of arguments");
		}
		String code = ((SimpleScalar)arguments.get(0)).getAsString();
		if (code == null || code.isEmpty()) {
			throw new TemplateModelException("Invalid code value '" + code + "'");
		}
		return messageSource.getMessage(code, null, locale);
	}
}