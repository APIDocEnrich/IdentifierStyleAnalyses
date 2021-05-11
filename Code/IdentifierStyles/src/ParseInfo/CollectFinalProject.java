package ParseInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class CollectFinalProject {
	
	public static void main(String args[]) throws Exception
	{
		HashSet<String> allproject=new HashSet<String>();
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\repository_state.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			int index=line.indexOf("github.com/");
			String url="https://"+line.substring(index, line.lastIndexOf(".git"));
			
			String foldername="";
			String[] split=url.split("/");
			if(split.length>4){
				foldername=split[3]+"_"+split[4];
			}
			else{
				foldername=split[3]+"_"+split[3];
			}
			
			File f=new File("D:\\project\\IdentifierStyle\\data\\GitProject\\"+foldername+"\\");
			if(f.exists())
			{
				
				
				int label1=0, label2=0;
				

				BufferedReader reader=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\GitWebInfo_realissue.csv"));
				String oneline="";
				while((oneline=reader.readLine())!=null)
				{
					if(oneline.startsWith(foldername+","))
					{
						label1=1;
						System.out.println(foldername);
						break;
					}
				}
				reader.close();
				
				int lineno=0;
				reader=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\IdentifierList\\"+foldername+".csv"));
				oneline="";
				while((oneline=reader.readLine())!=null)
				{
					lineno++;
				}
				reader.close();
				
				if(lineno>=100)
					label2=1;
				
				if(label1==1&&label2==1)
				{
					allproject.add(foldername);
				}
				
			}
			
		}
		br.close();
		
		
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\FinalProjects_realissue.txt"));
		for(String s:allproject)
		{
			bw.write(s);
			bw.newLine();
		}
		bw.close();
	}

}
