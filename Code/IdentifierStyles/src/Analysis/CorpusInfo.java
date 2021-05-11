package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class CorpusInfo {
	
	public static void main(String args[]) throws Exception
	{
		int count=0;
		
		Set<String> allproject=new HashSet<String>();
		BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Desktop\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			String project=line.substring(0, line.indexOf(","));
			allproject.add(project);
		}
		br.close();
		
		for(String project:allproject)
		{
			BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\IdentifierList\\"+project+".csv"));
			String single="";
			while((single=read.readLine())!=null)
			{
				count++;
			}
			read.close();
		}
		
		System.out.println(allproject.size()+"    "+count);
	}
	
	
	
	
	public static void main1(String args[]) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\projectinformation.csv"));
		int id=0;
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\FinalProjects_all.txt"));
		String line="";
		while((line=br.readLine())!=null)
		{
			String project=line;
			System.out.println(id+"   "+project);
			id++;
			BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\GitWebInfo.csv"));
			String info="";
			while((info=read.readLine())!=null)
			{
				if(info.startsWith(project+","))
				{
					info=info.substring(info.indexOf(",")+1, info.length());
					int watchernumber=Integer.parseInt(info.substring(0, info.indexOf(",")));	
					info=info.substring(info.indexOf(",")+1, info.length());
					int starnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int forknumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int issuenumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int requestnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int commitnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int branchnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int releasenumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
					info=info.substring(info.indexOf(",")+1, info.length());
					int usedby=Integer.parseInt(info.substring(0, info.indexOf(",")));
					int contributor=Integer.parseInt(info.substring(info.indexOf(",")+1, info.length()-1));
					
					int fileno=0;
					int lineno=0;
					BufferedReader reader=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+project+".txt"));
					String oneline="";
					while((oneline=reader.readLine())!=null)
					{
						if(!oneline.contains("?"))
						{
							fileno++;
							BufferedReader redd=new BufferedReader(new FileReader(oneline));
							String state="";
							while((state=redd.readLine())!=null)
							{
								if(!state.trim().isEmpty())
									lineno++;
							}
							redd.close();
						}
					}
					reader.close();
					
					bw.write(project+","+watchernumber+","+starnumber+","+forknumber+","+issuenumber+","+requestnumber+","+commitnumber+","+branchnumber+","+releasenumber+","+usedby+","+contributor+","+fileno+","+lineno);
					bw.newLine();
					
					break;
				}
			}
			read.close();
			
		}
		br.close();
		bw.close();
	}

}
