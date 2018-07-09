package co.th.aten.network.web;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.jboss.solder.logging.Logger;

import com.google.common.io.ByteStreams;

@ViewScoped
@Named
public class MasternodeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@PostConstruct
	public void init(){
		log.info("init method new");
		try{
			Map<String, String> header = FacesContext.getCurrentInstance().
					getExternalContext().getRequestHeaderMap();
			log.info("header size : "+header.size());
			String signature = header.get("X-Line-Signature");
			String connection = header.get("Connection");
			log.info("X-Line-Signature : "+signature);
			log.info("Connection : "+connection);
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().
					getExternalContext().getRequest();
			byte[] data = ByteStreams.toByteArray(request.getInputStream());
			String json = new String(data);
			log.info("json : "+json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void connect(){
		log.info("connect method ");
	}

}
