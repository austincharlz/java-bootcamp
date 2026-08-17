package com.northstar.crm.soap;

import com.northstar.crm.model.Customer;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

@Component
public class CustomerSoapMapper {
    static final String NAMESPACE_URI = "http://northstar.com/crm/customers";

    public String customerIdFromGetRequest(Element request) {
        NodeList matches = request.getElementsByTagNameNS(NAMESPACE_URI, "customerId");
        if (matches.getLength() == 0) {
            throw new IllegalArgumentException("customerId is required");
        }

        String customerId = matches.item(0).getTextContent().trim();
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        return customerId;
    }

    public Element toGetCustomerResponse(Customer customer) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().newDocument();

            Element response = document.createElementNS(NAMESPACE_URI, "ns2:GetCustomerResponse");
            document.appendChild(response);
            appendChild(document, response, "customerId", customer.getId());
            appendChild(document, response, "name", customer.getName());
            appendChild(document, response, "email", customer.getEmail());
            appendChild(document, response, "status", customer.getStatus());
            return response;
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to build GetCustomerResponse", ex);
        }
    }

    private void appendChild(Document document, Element parent, String elementName, String value) {
        Element child = document.createElementNS(NAMESPACE_URI, "ns2:" + elementName);
        child.setTextContent(value);
        parent.appendChild(child);
    }
}
