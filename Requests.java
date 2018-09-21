package com.vane.montse.asistente;

public class Requests {

    //public static final String IP = "192.168.1.84";
    public static final String IP = "192.168.100.109";
    public static final String PORT = "8088";
    public static final String  URL = "http://" + IP + ":" + PORT + "/pt1.pt2/";
    public static final String URL_LOGIN = URL + "webapi/personal/getLogin?pass=#pass&user=#user";
    public static final String URL_GETSTUDENTS = URL + "webapi/asistente/getAssignedStudents?id=#id&name=#name";

}
