package br.com.cauezito.util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Retorna PDF em byte para download
	public byte[] gerarRelatorio(List dados, String relatorio, ServletContext servletContext) {
		
		//Cria a lista de dados para o relatório com a lista de objetos passada
		JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(dados);
		
		//Define o caminho do arquivo jasper compilado
		String caminho = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		
		try {
			//Carrega o arquivo jasper com os dados passados
			JasperPrint impressoraJasper = JasperFillManager.fillReport(caminho, new HashMap<>(), jrBean);
			return JasperExportManager.exportReportToPdf(impressoraJasper);
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

}
