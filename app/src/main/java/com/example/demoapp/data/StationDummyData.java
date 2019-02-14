package com.example.demoapp.data;

import com.example.demoapp.models.PoliceStation;

import java.util.ArrayList;

public class StationDummyData {


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

        return stations;

    }
}
