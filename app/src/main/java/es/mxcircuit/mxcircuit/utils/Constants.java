package es.mxcircuit.mxcircuit.utils;

/**
 * Created by Gashe on 15/5/17.
 */

public final class Constants {

    public static final Api API = new Api();
    public static final String URL_SERVER = "http://www.mxcircuit.es/";
    public static final String MAIL = "info@mxcircuit.es";
    public static final Web WEB = new Web();
    public static final Code CODES = new Code();
    public static final Keys KEYS = new Keys();
    public static final Bundle BUNDLE = new Bundle();

    public static final class Api{

        public static final String URL = "admin/api/";
        public static final String USER_EXIST = "userExistv2";
        public static final String REGISTER_USER = "registerTESTER";
        public static final String CIRCUIT_LIST = "getListv2";
        public static final String DATA_CIRCUIT = "getDataCircuitv2";
        public static final String NOTIFICATION_USER_LIST = "getNotificationsUser";
        public static final String NOTIFICATION_USER_LIST_TO_CIRCUIT = "getNotificationsUserToCircuit";
        public static final String VOTE_CIRCUIT = "votev2";
        public static final String SETTING = "getSetting";
        public static final String CHANGE_SETTING = "setSetting";
        public static final String CHANGE_TOKEN = "setToken";
        public static final String WEATHER = "getWeather";

        private Api() {

        }
    }

    public static final class Web{

        public static final String ADD_CIRCUIT = "circuitos/alta";
        public static final String LOGIN_CIRCUIT = "admin/login";
        public static final String DOSSIER = "dossier";

        private Web() {

        }
    }

    public static final class Bundle{

        public static final String DATA_CIRCUIT = "datacircuit";
        public static final String ID_CIRCUIT = "idcircuit";
        public static final String URL_WEBVIEW = "urlwebview";

        private Bundle() {

        }
    }

    public static final class Code{

        public static final int PERMISSION_LOCATION = 100;

        private Code() {

        }
    }

    public static final class Keys{

        public static final String GOOGLE_API = "AIzaSyCZ0N_hhY03Jlm2hc3QpI9OkHd-ytfnTMM";

        private Keys() {

        }
    }

    private Constants() {
        throw new AssertionError();
    }
}
