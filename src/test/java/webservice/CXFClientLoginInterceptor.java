/**
 * Demeter CRM KIT
 *
 * @project demeter_crm
 * @package com.movitech.demeter_crm
 * @author kai-f
 * @create 2017-06-25 10:44
 * @update <br/>2017-06-25 @kai-f -- create
 */
package webservice;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

public class CXFClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private String username;
	private String password;

	public CXFClientLoginInterceptor(String username, String password) {
		super(Phase.PREPARE_SEND);
		this.username = username;
		this.password = password;
	}

	@Override
	public void handleMessage(SoapMessage soap) throws Fault {
		List<Header> headers = soap.getHeaders();
		Document doc = DOMUtils.createDocument();
		Element auth = doc.createElementNS("http://tempuri.org/","SecurityHeader");
		Element username = doc.createElement("username");
		Element password = doc.createElement("password");
		username.setTextContent(this.username);
		password.setTextContent(this.password);
		auth.appendChild(username);
		auth.appendChild(password);
		headers.add(0, new Header(new QName("SecurityHeader"),auth));
	}
}
