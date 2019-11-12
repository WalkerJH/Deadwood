// Example Code for parsing XML file
// Dr. Moushumi Sharmin
// CSCI 345

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class ParseXML{

    private Document d;

    public ParseXML(String filename) throws Exception {
        d = getDocFromFile(filename);
    }

    // building a document from the XML file
        // returns a Document object after loading the book.xml file.
        public Document getDocFromFile(String filename) throws ParserConfigurationException {
           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = dbf.newDocumentBuilder();
           Document doc = null;
           
           try{
               doc = db.parse(filename);
           } catch (Exception ex){
               System.out.println("XML parse failure");
               ex.printStackTrace();
           }

           return doc;
        }
        
        // reads data from XML file and prints data
        public Board readBoardData() {
            Element root = d.getDocumentElement();
            Board board = new Board();
            NodeList sets = root.getElementsByTagName("set");

            //read in location names
            for (int i = 0; i < sets.getLength(); i++) {
                //reads data from the nodes
                Node currentNode = sets.item(i);
                String name = currentNode.getAttributes().getNamedItem("name").getNodeValue();
                Location l = new Location(name);
                board.add(l);
            }

            //hard code in Trailer
            Location trailer = new Location("trailer");
            trailer.addNeighbors(board.findLocation("Main Street"), board.findLocation("Saloon"),
                    board.findLocation("Hotel"));
            board.add(trailer);
            //hard code casting office
            Location office = new Location("office");
            office.addNeighbors(board.findLocation("Train Station"), board.findLocation("Ranch"),
                    board.findLocation("Secret Hideout"));
            board.add(office);

            //read in other data
            for (int i = 0; i < sets.getLength(); i++) {
                Node currentNode = sets.item(i);
                Location currentLocation = board.getLocations().get(i);
                NodeList children = currentNode.getChildNodes();
                Set set = new Set();

                for (int j = 0; j < children.getLength(); j++) {
                    Node sub = children.item(j);
                    //read in neighbors
                    if ("neighbors".equals(sub.getNodeName())) {
                        NodeList neighbors = sub.getChildNodes();
                        for (int k = 1; k < neighbors.getLength(); k += 2) {
                            Node neighbor = neighbors.item(k);
                            String name = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                            currentLocation.addNeighbors(board.findLocation(name));
                        }
                    }
                    //read in roles
                    else if ("parts".equals(sub.getNodeName())) {
                        NodeList roles = sub.getChildNodes();
                        for (int k = 1; k < roles.getLength(); k+=2) {
                            Node role = roles.item(k);
                            String name = role.getAttributes().getNamedItem("name").getNodeValue();
                            int rank = Integer.parseInt(role.getAttributes().getNamedItem("level").getNodeValue());
                            NodeList roleDetails = role.getChildNodes();
                            Node line = role.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild();
                            String desc = line.getNodeValue();
                            set.addRole(new ExtraRole(name, desc, rank));
                        }
                    }
                    //read number of shot counters
                    else if ("takes".equals(sub.getNodeName())) {
                        NodeList shots = sub.getChildNodes();
                        set.setShotCounters(shots.getLength()/2);
                    }
                }
                currentLocation.setSet(set);
            }
            office.setName("Casting Office");
            trailer.setName("Trailer");
            return board;
        }
}//class