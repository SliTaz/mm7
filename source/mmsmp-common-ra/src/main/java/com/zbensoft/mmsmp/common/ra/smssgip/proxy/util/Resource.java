package com.zbensoft.mmsmp.common.ra.smssgip.proxy.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Resource {
    private Element root;

    public Resource(URL url) {
        this.init(url.toString());
    }

    public Resource(String resource) {
        String file = resource;
        InputStream url = null;
        url = Resource.class.getResourceAsStream("/config.xml");

        do {
            if (url != null) {
                try {
                    SAXReader reader = new SAXReader();
                    Document dom = reader.read(url);
                    this.root = dom.getRootElement();
                } catch (DocumentException var6) {
                    var6.printStackTrace();
                }

                return;
            }

            url = Resource.class.getResourceAsStream("/" + file + ".xml");
        } while(url != null);

        throw new MissingResourceException("Can't find resource url:" + file + ".xml", this.getClass().getName(), (String)null);
    }

    public Resource(Class c, String url) throws IOException {
        String className = c.getName();
        int i = className.lastIndexOf(46);
        if (i > 0) {
            className = className.substring(i + 1);
        }

        URL u = new URL(c.getResource(className + ".class"), url);
        this.init(u.toString());
    }

    public void init(String url) {
        url = url + '_' + Locale.getDefault();

        while(true) {
            try {
                SAXReader reader = new SAXReader();
                Document dom = reader.read(url + ".xml");
                this.root = dom.getRootElement();
                return;
            } catch (DocumentException var4) {
                if (!(var4.getNestedException() instanceof FileNotFoundException)) {
                    var4.printStackTrace();
                }

                int i = url.lastIndexOf(95);
                if (i < 0) {
                    throw new MissingResourceException("Can't find resource url:" + url + ".xml", this.getClass().getName(), (String)null);
                }

                url = url.substring(0, i);
            }
        }
    }

    public String get(String xpath) {
        Node node = this.root.selectSingleNode(xpath);
        return node == null ? null : node.getText();
    }

    public Parameter getParameter(String xpath) {
        List list = this.root.selectNodes(xpath);
        if (list == null) {
            return null;
        } else {
            Iterator it = list.iterator();
            HashMap map = new HashMap();

            while(it.hasNext()) {
                Element e = (Element)it.next();
                map.put(e.getName(), e.getText());
            }

            return new Parameter(map);
        }
    }

    public static void main(String[] args) throws IOException {
        Resource resource = new Resource(Resource.class, "resource");
        System.out.println(resource.get("smproxy/nonlicetsp-logname"));
        System.out.println(resource.getParameter("comm/*"));
        System.out.println(Resource.class.getResourceAsStream("/app.xml"));
    }
}
