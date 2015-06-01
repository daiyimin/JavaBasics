package com.ericsson.tc.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class HwpfTest {
	 
	   public static void main(String...strings) throws Exception {
	      InputStream is = new FileInputStream("c:\\temp\\2400-5-CAX1055181.doc");
	      WordExtractor extractor = new WordExtractor(is);
	      //���word�ĵ����е��ı�
//	      System.out.println(extractor.getText());
	      System.out.println(extractor.getTextFromPieces());
	      //���ҳü������
//	      System.out.println("ҳü��" + extractor.getHeaderText());
	      //���ҳ�ŵ�����
//	      System.out.println("ҳ�ţ�" + extractor.getFooterText());
	      //�����ǰword�ĵ���Ԫ������Ϣ���������ߡ��ĵ����޸�ʱ��ȡ�
//	      System.out.println(extractor.getMetadataTextExtractor().getText());
	      //��ȡ����������ı�
/*	      String paraTexts[] = extractor.getParagraphText();
	      for (int i=0; i<paraTexts.length; i++) {
	         System.out.println("Paragraph " + (i+1) + " : " + paraTexts[i]);
	      }
*/	      //�����ǰword��һЩ��Ϣ
//	      printInfo(extractor.getSummaryInformation());
	      //�����ǰword��һЩ��Ϣ
//	      printInfo(extractor.getDocSummaryInformation());
	      closeStream(is);
	   }
	  
	   /**
	    * ���SummaryInfomation
	    * @param info
	    */
	   private static void printInfo(SummaryInformation info) {
	      //����
	      System.out.println(info.getAuthor());
	      //�ַ�ͳ��
	      System.out.println(info.getCharCount());
	      //ҳ��
	      System.out.println(info.getPageCount());
	      //����
	      System.out.println(info.getTitle());
	      //����
	      System.out.println(info.getSubject());
	   }
	  
	   /**
	    * ���DocumentSummaryInfomation
	    * @param info
	    */
	   private static void printInfo(DocumentSummaryInformation info) {
	      //����
	      System.out.println(info.getCategory());
	      //��˾
	      System.out.println(info.getCompany());
	   }
	  
	   /**
	    * �ر�������
	    * @param is
	    */
	   private static void closeStream(InputStream is) {
	      if (is != null) {
	         try {
	            is.close();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	   }
	  
	}