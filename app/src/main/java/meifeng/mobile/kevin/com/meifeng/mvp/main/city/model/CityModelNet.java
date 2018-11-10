package meifeng.mobile.kevin.com.meifeng.mvp.main.city.model;

import java.util.ArrayList;
import java.util.List;

public class CityModelNet {

    private ArrayList<ProvinceModel> Provinces;

    public ArrayList<ProvinceModel> getProvinces() {
        return Provinces;
    }

    public void setProvinces(ArrayList<ProvinceModel> provinces) {
        Provinces = provinces;
    }

    @Override
    public String toString() {
        return "CityModelNet{" +
                "Provinces=" + Provinces +
                '}';
    }

    public ProvinceModel.CityModel getCModel(){
        return new ProvinceModel().getCModel();
    }

    public ProvinceModel getPModel(){
        return new ProvinceModel();
    }

    public class ProvinceModel {
        private String Name;
        private ArrayList<CityModel> Cities;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public ArrayList<CityModel> getCities() {
            return Cities;
        }


        public CityModel getCModel(){
            return new CityModel();
        }

        @Override
        public String toString() {
            return "ProvinceModel{" +
                    "Name='" + Name + '\'' +
                    ", Cities=" + Cities +
                    '}';
        }

        public void setCities(ArrayList<CityModel> cities) {
            Cities = cities;
        }

        public class CityModel {
            private String Name;


            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            @Override
            public String toString() {
                return "CityModel{" +
                        "Name='" + Name + '\'' +
                        '}';
            }
        }

    }


}
