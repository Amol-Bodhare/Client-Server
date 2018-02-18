package com.company;
import sun.org.mozilla.javascript.internal.ast.WhileLoop;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server
{
    private static boolean isBathroomVacant=true;
    public boolean getIsBathroomVacant() {
        return isBathroomVacant;
    }

    public void setBathroomVacant(boolean bathroomVacant) {
        isBathroomVacant = bathroomVacant;
    }


    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

            Socket s = null;
            ServerSocket ss2 = null;
            System.out.println("Server Listening......");
            try {
                ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server error");

            }

            while (true) {
                try {
                    s = ss2.accept();
                    System.out.println("connection Established");
                    ServerThread st = new ServerThread(s);
                    st.start();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Connection Error");

                }
            }

        }


}

class ServerThread extends Thread{

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;

    public ServerThread(Socket s){
        this.s=s;
    }

    public void run() {
        try{
            is= new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());

        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line=is.readLine();
            while(line.compareTo("QUIT")!=0){
                Server s=new Server();
                if(line.equals("OUT"))
                {
                    s.setBathroomVacant(true);
                }
                else if(line.equals("IN"))
                {
                    s.setBathroomVacant(false);
                }
                System.out.println("message from client "+line);
                os.println(s.getIsBathroomVacant());

                os.flush();
                System.out.println("Bathroom is engaged  :  "+s.getIsBathroomVacant());
                line=is.readLine();
            }
        } catch (IOException e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
            try{
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Closed");
                }

            }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}