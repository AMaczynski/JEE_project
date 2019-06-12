package pl.edu.agh.webapp;

public class Utils {
    public static String statusToString(int status) {
        switch(status) {
            case Const.ORDER_PLACED:
                return "Order placed";

            case Const.ORDER_PREPARING:
                return "Preparing";

            case Const.ORDER_PREPARED:
                return "Prepared";

            case Const.ORDER_DELIVERING:
                return "In delivery";

            case Const.ORDER_DELIVERED:
                return "Delivered";

            default:
                return "";
        }
    }
}
