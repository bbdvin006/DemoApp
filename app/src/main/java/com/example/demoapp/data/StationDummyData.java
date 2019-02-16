package com.example.demoapp.data;

import com.example.demoapp.models.PoliceStation;

import java.util.ArrayList;

public class    StationDummyData {


    public static ArrayList<PoliceStation> load() {

        ArrayList<PoliceStation> stations = new ArrayList<>();

        stations.add(new PoliceStation(
                "Police Station-Gudumba",
                "Raj Vihar, Phool Bagh Colony, Lucknow, Uttar Pradesh 226022",
                "Gudamba",
                "05222361155", 26.919067, 80.964474)
        );
        stations.add(new PoliceStation(
                "Police Chowki-Hazratganj",
                "Mahatma Gandhi Marg, Qaiserbagh, Hazratganj, Lucknow, Uttar Pradesh 226001",
                "Hazratganj",
                "05222361155", 26.855448, 80.936198)
        );
        stations.add(new PoliceStation(
                "Aliganj Police Chowki- kapoorthla",
                "Near Kaliji Mandir, Kapoorthla, Aliganj, Sector A, Chandralok, Lucknow, Uttar Pradesh 226020",
                "kapoorthla",
                "05222361155", 26.882199, 80.945811)
        );
        stations.add(new PoliceStation(
                "Madiyaon Police Station",
                "Sitapur Rd, Near Over Bridge, Mohibullapur, Madiyanva, Lucknow, Uttar Pradesh 226021",
                "Madiyaon       ",
                "09454403864", 26.913857, 80.937591)
        );
        stations.add(new PoliceStation(
                "Ashiyana Police Station",
                "Quila road, Near, Power House Chauraha, Ashiyana, Lucknow, Uttar Pradesh 226012",
                "Aashiana",
                "09454403841", 26.799559, 80.917397)
        );
        stations.add(new PoliceStation(
                "Police Station Alambagh",
                "NEAR, Railway Colony, Alambagh, Lucknow, Uttar Pradesh 226005",
                "Alambagh",
                "9454401489",26.8265, 80.9094)
        );
        stations.add(new PoliceStation(
                "Aishbagh Police Station",
                "278/34/5, Aishbagh Rd, Moti Jheel Colony, Aishbagh, Lucknow, Uttar Pradesh 226004",
                "Aishbagh",
                "08089891818", 26.8316, 80.9224
        ));
        stations.add(new PoliceStation(
           "Husainganj Police Station",
           "12/52, Cantonment Rd, Udaiganj, Husainganj, Lucknow, Uttar Pradesh 226001",
           "Hussainganj",
           "9651579936",26.8371, 80.9381
        ));
        stations.add(new PoliceStation(
                "Chowk Police Station",
                "Gol Darwaza, near radheylal sweets, Chowk, Lucknow, Uttar Pradesh 226003",
                "Chowk",
                "05222255742", 26.8649,80.9093
        ));
        stations.add(new PoliceStation(
                "Mahanagar Police Station",
                "Mahanagar, Lucknow, Uttar Pradesh 226006",
                "Mahanagar",
                "05222385151" , 26.8731, 80.9589
        ));
        stations.add(new PoliceStation(
                "Police Station Cantt",
                "100, Nehru Rd, Sadar Bazaar, Cantonment, Lucknow, Uttar Pradesh 226002",
                "Cantt",
                "09454403845", 26.8254, 80.9460
        ));
        stations.add(new PoliceStation(
                "Aminabad Police Station",
                "Shri Ram Rd, Aminabad, Lucknow, Uttar Pradesh 226018",
                "Aminabad",
                "05222624635", 26.8462, 80.9267
        ));


        return stations;

    }
}
