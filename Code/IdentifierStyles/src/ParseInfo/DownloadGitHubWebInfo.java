package ParseInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class DownloadGitHubWebInfo {
	
	 
	
	
	
	
	public static void DownloadSingleWebPage(String url,String foldername) throws Exception
	{
		try
		{
			Document doc = Jsoup.connect(url).get();
			String content=doc.html();	
			BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\GitWebInfo\\"+foldername+".html")); 
			bw.write(content);
			bw.close();
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
		

	}

	
	public static void ParseInfo() throws Exception
	{	
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\GitWebInfo_realissue.csv"));
		
		File f=new File("D:\\project\\IdentifierStyle\\data\\GitWebInfo\\");
		File[] lists=f.listFiles();
		for(int i=0;i<lists.length;i++)
		{	
			int watchernumber=0;
			int starnumber=0;
			int forknumber=0;
			int issuenumber=0;
			int requestnumber=0;
			int commitnumber=0;
			int branchnumber=0;
			int releasenumber=0;
			int contributor=0;
			int usedby=0;
			int label=0;
			
			String filename=lists[i].getName();
			filename=filename.substring(0, filename.lastIndexOf(".html"));
			System.out.println(filename);
			BufferedReader read=new BufferedReader(new FileReader(lists[i].getAbsolutePath()));
			String readline="";
			while((readline=read.readLine())!=null)
			{
				if(readline.contains("Watch </a> <a"))
				{
					
					readline=readline.substring(0,readline.indexOf("</a> </li>")).trim();
					String watcherno=readline.substring(readline.lastIndexOf(">")+1,readline.length()).trim();
					watchernumber=ParseNumber(watcherno);						
				}
				if(readline.contains("Star </a> <a"))
				{
					
					readline=readline.substring(0,readline.indexOf("</a> </li>")).trim();
					String starno=readline.substring(readline.lastIndexOf(">")+1,readline.length()).trim();
					starnumber=ParseNumber(starno);						
				}
				if(readline.contains("Fork </a> <a"))
				{
					
					readline=readline.substring(0,readline.indexOf("</a> </li>")).trim();
					String forkno=readline.substring(readline.lastIndexOf(">")+1,readline.length()).trim();
					forknumber=ParseNumber(forkno);
					

				}
								
				if(readline.contains("Issues</span> <span"))
				{
					readline=readline.substring(0,readline.lastIndexOf("</span>")).trim();
					String issues=readline.substring(readline.lastIndexOf(">")+1,readline.length()).trim();
					issuenumber=ParseNumber(issues);
					label=1;
				}
				
				if(readline.contains("Pull requests</span>"))
				{
					readline=readline.substring(0,readline.lastIndexOf("</span>")).trim();
					String request=readline.substring(readline.lastIndexOf(">")+1,readline.length()).trim();
					requestnumber=ParseNumber(request);
					
				}
												
				if(readline.contains("inline\">commits</span>"))
				{
					String commitno=readline.substring(readline.indexOf("<strong>")+8,readline.indexOf("</strong>")).trim();
					commitnumber=ParseNumber(commitno);
				}
				
				if(readline.contains("light\">branches</span>")||readline.contains("light\">branch</span>"))
				{
					String branchno=readline.substring(readline.indexOf("<strong>")+8,readline.indexOf("</strong>")).trim();
					branchnumber=ParseNumber(branchno);
				}
				
				if(readline.contains("text-gray-light\">tags</span>"))
				{
//					System.out.println(filename+"   "+readline.indexOf("<strong>")+"   "+readline.indexOf("</strong>"));
					String releaseno=readline.substring(readline.indexOf("<strong>")+8,readline.indexOf("</strong>")).trim();
					releasenumber=ParseNumber(releaseno);
				}	
				
				if(readline.contains("> Used by <span"))
				{
					String usedbyno=readline.substring(readline.indexOf("class=\"Counter \">")+"class=\"Counter \">".length(), readline.indexOf("</span>")).trim();
					if(usedbyno.contains("+"))
						usedbyno=usedbyno.replace("+", "");
					usedby=ParseNumber(usedbyno);
				}
				
				if(readline.contains("> Contributors <span"))
				{
					String contributornu=readline.substring(readline.indexOf("class=\"Counter \">")+"class=\"Counter \">".length(), readline.indexOf("</span>")).trim();
					if(contributornu.contains("+"))
						contributornu=contributornu.replace("+", "");
					contributor=ParseNumber(contributornu);
				}
					
			}
			read.close();
				
			if(label==1)
			{
				bw.write(filename+","+watchernumber+","+starnumber+","+forknumber+","+issuenumber+","+requestnumber+","+commitnumber+","+branchnumber+","+releasenumber+","+usedby+","+contributor+",");
				bw.newLine();
			}
			
			
		}
		
		bw.close();
		
	}
	
	public static int ParseNumber(String value)
	{
		if(value.contains("k"))
		{
			double front=Double.parseDouble(value.substring(0, value.indexOf("k")));
			front=front*1000;
			return (int)front;
		}
		else if(value.contains(","))
		{
			String pure=value.replace(",", "");
			return Integer.parseInt(pure);
		}
		else
		{
			return Integer.parseInt(value);
		}
	}

	
	
	
	 
}
