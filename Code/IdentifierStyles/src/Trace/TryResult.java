package Trace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

public class TryResult {
	
	 

	
	public static void ParseAnalysisResult() throws Exception
	{
		Hashtable<String,Integer> hs=new Hashtable<String,Integer>();
		Hashtable<String,Integer> multihs=new Hashtable<String,Integer>();
		
		int changedcount=0,stylechangedcount=0;
		
		BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Desktop\\styled_changed1.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			
				String split[]=line.split(",");
				String trans=split[4];
				String transpattern="";
				String onetrans[]=trans.split("<-");
				
				for(int i=1;i<onetrans.length;i++)
				{
					if(!onetrans[i].equals(onetrans[i-1]))
					{
						//it means that identifier has changed
						
						changedcount++;
						
						String style1=CheckStyle(onetrans[i]);
						String style2=CheckStyle(onetrans[i-1]);
						if(!style1.equals(style2))
						{	
							stylechangedcount++;
							
							String classification=style1+"->"+style2;
							transpattern=transpattern+"->"+classification;
							
							if(hs.keySet().contains(classification))
							{
								int value=hs.get(classification);
								value++;
								hs.remove(classification);
								hs.put(classification, value);
							}
							else
							{
								hs.put(classification, 1);
							}
						}
						
					}
				}
				
				if(multihs.keySet().contains(transpattern))
				{
					int value=multihs.get(transpattern);
					value++;
					multihs.remove(transpattern);
					multihs.put(transpattern, value);
				}
				else
				{
					multihs.put(transpattern, 1);
				}
				
					 
				
		}
		br.close();

		
		
		BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\result.csv"));
		Set<String> keys=hs.keySet();
		for(String onekey:keys)
		{
			bw.write(onekey+","+hs.get(onekey));
			bw.newLine();
		}
		bw.write("Style Changed Count, "+stylechangedcount);
		bw.newLine();
		bw.write("Changed Count, "+changedcount);
		bw.newLine();
		Set<String> keyss=multihs.keySet();
		for(String onekey:keyss)
		{
			bw.write(onekey+","+multihs.get(onekey));
			bw.newLine();
		}
		bw.close();
	}

	public static String CheckStyle(String IdentifierName)
	{
		if(IdentifierName.contains("_"))
		{
			return "underscore";
		}
		else
		{
			LinkedList<String> result=Analysis.StyleInfluence.CamelCaseSplit(IdentifierName);
			if(result.size()>1)
			{
				return "camel";					
			}
			else if(result.size()==1)
			{
				return "others";
			}
			else
			{
				return "null";
			}
		}
	}
}
