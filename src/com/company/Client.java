package com.company;
import java.net.*;
import java.io.*;

public class Client
{

    public static void main(String args[]) throws IOException{


        InetAddress address=InetAddress.getLocalHost();
        Socket s1=null;
        String line=null;
        BufferedReader br=null;
        BufferedReader is=null;
        PrintWriter os=null;

        try {
            s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
            br= new BufferedReader(new InputStreamReader(System.in));
            is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os= new PrintWriter(s1.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        try{
            line=br.readLine();
            while(line.compareTo("QUIT")!=0){
                System.out.println("Message sent "+line);
                os.println(line);
                os.flush();
                response=is.readLine();
                if(Boolean.parseBoolean(response))
                    System.out.println("The bathroom is vacant");
                else
                    System.out.println("The bathroom is engaged");
                line=br.readLine();

            }



        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{

            is.close();os.close();br.close();s1.close();
            System.out.println("Connection Closed");

        }

    }
}