package pl.edu.agh.webapp;

import pl.edu.agh.datamodel.Address;

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

            case Const.ORDER_CANCELED:
                return "Canceled";

            default:
                return "";
        }
    }

    public static String roleToString(int status) {
        switch(status) {
            case Const.ROLE_CLIENT:
                return "Client";

            case Const.ROLE_MANAGER:
                return "Manager";

            case Const.ROLE_COOK:
                return "Cook";

            case Const.ROLE_DRIVER:
                return "Driver";

            default:
                return "";
        }
    }

    public static String addressToString(Address address) {
        if (address == null)
            return "Collect in person";
        else {
            return String.format("%s %s %d/%d", address.getCity(), address.getStreet(), address.getBuildingNumber(), address.getApartmentNumber());
        }
    }
}
