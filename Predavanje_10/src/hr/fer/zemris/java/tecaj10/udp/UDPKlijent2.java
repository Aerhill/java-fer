package hr.fer.zemris.java.tecaj10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPKlijent2 {

public static void main(String[] args) throws SocketException, UnknownHostException {
		
		if(args.length != 4) {
			System.out.println("Očekivao sam host port broj1 broj2");
			return;
		}
		
		String hostName = args[0];
		int port = Integer.parseInt(args[1]);
		
		short broj1 = Short.parseShort(args[2]);
	
		byte[] podatci = new byte[2];
		ShortUtil.shortToBytes(broj1, podatci, 0);
		String poruka = new String(podatci);
		poruka += args[3];
		podatci = poruka.getBytes();
		DatagramSocket dSocket = new DatagramSocket();
		
		DatagramPacket packet = new DatagramPacket(podatci, podatci.length);
		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostName), port));
		
		// koliko ćemo čekati odgovor
		dSocket.setSoTimeout(4000);
		boolean answerReceived = false;
		// šalji sve dok ne dobiješ odgovor u roku 4 sekunde (timeout)
		while(!answerReceived) {
			System.out.println("Šaljem upit...");
			try {
				dSocket.send(packet);
			} catch (IOException e) {
				System.out.println("Ne mogu poslati upit.");
				break;
			}
			
			
			byte[] recvBuffer = new byte[4];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			try {
				dSocket.receive(recvPacket);
			} catch (SocketTimeoutException ste) {
				// ako je istekao timeout, ponovno šalji upit
				continue;
			} catch (IOException e) {
				// u slučaju drugih pogrešaka- najbolje se dogovoriti šta raditi
				continue;
			}
			
			if (recvPacket.getLength() != 2) {
				System.out.println("Primljen je odgovor neočekivane duljine");
				break;
			}
			
			System.out.println("Rezultat je: " + ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset()));
			answerReceived = true;
		}
		// obavezno zatvori pristupnu točku
		dSocket.close();
	}
}
