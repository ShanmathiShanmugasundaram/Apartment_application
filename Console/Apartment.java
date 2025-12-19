import java.util.*;
import java.text.*;
class ApartmentVisitorApp {
    static Scanner sc = new Scanner(System.in);
    static class User {
        String username, password, role, apartmentNumber;
        User(String u, String p, String r, String a) {
            username = u;
            password = p;
            role = r;
            apartmentNumber = a;
        }
    }
    static class Visitor {
        String name, vehicleNumber, apartmentNumber;
        boolean approved = false;
        boolean checkedIn = false;
        boolean checkedOut = false;
        String parkingSlot = "None";
        String arrivalTime = "-";
        String checkoutTime = "-";
        Visitor(String n, String v, String a) {
            name = n;
            vehicleNumber = v;
            apartmentNumber = a;
        }
    }
    static List<User> users = new ArrayList<>();
    static List<Visitor> visitors = new ArrayList<>();
    static Set<String> parkingSlots = new HashSet<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
    static Map<String, String> occupiedSlots = new HashMap<>();

    public static void main(String[] args) {
        users.add(new User("admin", "admin", "Admin", "-")); // Default Admin
        while (true) {
            System.out.println("\n=== Apartment Visitor Entry App ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                register();
            } else if (ch == 2) {
                login();
            } else if (ch == 3) {
                System.out.println("Thankyou!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    static void register() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        System.out.print("Role (Resident/Security): ");
        String r = sc.nextLine();
        String a = "-";
        if (r.equalsIgnoreCase("Resident")) {
            System.out.print("Apartment Number: ");
            a = sc.nextLine();
        }
        users.add(new User(u, p, r, a));
        System.out.println("Registered Successfully!\n");
    }
    static void login() {
        System.out.println("\n--- User Login ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        for (User user : users) {
            if (user.username.equals(u) && user.password.equals(p)) {
                System.out.println("Login Successful as " + user.role + "!\n");
                if (user.role.equalsIgnoreCase("Resident")) {
                    residentMenu(user);
                } else if (user.role.equalsIgnoreCase("Security")) {
                    securityMenu(user);
                } else if (user.role.equalsIgnoreCase("Admin")) {
                    adminMenu();
                }
                return;
            }
        }
        System.out.println("Invalid Credentials!\n");
    }
    static void residentMenu(User user) {
        while (true) {
            System.out.println("\n--- Resident Menu ---");
            System.out.println("1. Pre-Register Visitor");
            System.out.println("2. Approve/Deny Visitor");
            System.out.println("3. View My Visitor Logs");
            System.out.println("4. Logout");
            System.out.print("Select: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                System.out.print("Visitor Name: ");
                String vname = sc.nextLine();
                System.out.print("Vehicle Number: ");
                String vnum = sc.nextLine();
                visitors.add(new Visitor(vname, vnum, user.apartmentNumber));
                System.out.println("Visitor Pre-Registered!\n");
            } else if (ch == 2) {
                boolean found = false;
                for (Visitor v : visitors) {
                    if (v.apartmentNumber.equals(user.apartmentNumber) && !v.approved) {
                        found = true;
                        System.out.println("- " + v.name + " (Vehicle: " + v.vehicleNumber + ")");
                        System.out.print("Approve? (yes/no): ");
                        String ans = sc.nextLine();
                        if (ans.equalsIgnoreCase("yes")) {
                            v.approved = true;
                            System.out.println("Visitor Approved!");
                        } else {
                            visitors.remove(v);
                            System.out.println("Visitor Denied and Removed!");
                            break; // avoid concurrent modification
                        }
                    }
                }
                if (!found) {
                    System.out.println("No Pending Visitors for Approval.");
                }

            } else if (ch == 3) {
                System.out.println("Your Visitor Logs:");
                for (Visitor v : visitors) {
                    if (v.apartmentNumber.equals(user.apartmentNumber)) {
                        System.out.println("- Name: " + v.name +
                                " | Approved: " + v.approved +
                                " | Checked In: " + v.checkedIn +
                                " | Checked Out: " + v.checkedOut +
                                " | Parking Slot: " + v.parkingSlot +
                                " | Arrival: " + v.arrivalTime +
                                " | Checkout: " + v.checkoutTime);
                    }
                }
            } else if (ch == 4) {
                System.out.println("Logged Out.\n");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    static void securityMenu(User user) {
        while (true) {
            System.out.println("\n--- Security Menu ---");
            System.out.println("1. Visitor Check-In");
            System.out.println("2. Visitor Check-Out");
            System.out.println("3. View All Visitor Logs");
            System.out.println("4. Logout");
            System.out.print("Select: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                System.out.print("Visitor Name: ");
                String vname = sc.nextLine();
                boolean found = false;
                for (Visitor v : visitors) {
                    if (v.name.equalsIgnoreCase(vname) && v.approved && !v.checkedIn) {
                        v.checkedIn = true;
                        assignParking(v);
                        System.out.println("Visitor Checked-In. Parking Slot Assigned: " + v.parkingSlot);
                        System.out.println("ALERT: Visitor " + v.name + " has arrived at Apartment " +"!");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Visitor not found or not approved!");
                }
            } else if (ch == 2) {
                System.out.print("Visitor Name: ");
                String vname = sc.nextLine();
                boolean found = false;
                for (Visitor v : visitors) {
                    if (v.name.equalsIgnoreCase(vname) && v.checkedIn && !v.checkedOut) {
                        v.checkedOut = true;
                        releaseParking(v);
                        System.out.println("Visitor Checked-Out. Parking Slot Released.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Visitor not found or not checked in!");
                }
            } else if (ch == 3) {
                System.out.println("All Visitor Logs:");
                for (Visitor v : visitors) {
                    System.out.println("- Name: " + v.name +
                            " | Apartment: " + v.apartmentNumber +
                            " | Approved: " + v.approved +
                            " | CheckedIn: " + v.checkedIn +
                            " | CheckedOut: " + v.checkedOut +
                            " | Parking: " + v.parkingSlot);
                }
            } else if (ch == 4) {
                System.out.println("Logged Out.\n");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Visitors");
            System.out.println("2. View Parking Slots Status");
            System.out.println("3. Generate Security Report");
            System.out.println("4. Logout");
            System.out.print("Select: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                System.out.println("Visitor Logs:");
                for (Visitor v : visitors) {
                    System.out.println("- Name: " + v.name +
                            " | Apartment: " + v.apartmentNumber +
                            " | Approved: " + v.approved +
                            " | CheckedIn: " + v.checkedIn +
                            " | CheckedOut: " + v.checkedOut +
                            " | Parking: " + v.parkingSlot +
                            " | Arrival: " + v.arrivalTime +
                            " | Checkout: " + v.checkoutTime);
                }
            } else if (ch == 2) {
                System.out.println("Parking Slots:");
                for (String slot : parkingSlots) {
                    if (occupiedSlots.containsKey(slot)) {
                        System.out.println(slot + " - Occupied by " + occupiedSlots.get(slot));
                    } else {
                        System.out.println(slot + " - Available");
                    }
                }
            } else if (ch == 3) {
                System.out.println("=== Security Audit Report ===");
                for (Visitor v : visitors) {
                    System.out.println("- Visitor: " + v.name +
                            " | Apartment: " + v.apartmentNumber +
                            " | Arrived: " + v.arrivalTime +
                            " | Checked Out: " + v.checkoutTime);
                }
            } else if (ch == 4) {
                System.out.println("Logged Out.\n");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    static void assignParking(Visitor v) {
        for (String slot : parkingSlots) {
            if (!occupiedSlots.containsKey(slot)) {
                occupiedSlots.put(slot, v.name);
                v.parkingSlot = slot;
                return;
            }
        }
        v.parkingSlot = "No Slot Available";
    }
    static void releaseParking(Visitor v) {
        if (v.parkingSlot != null && !v.parkingSlot.equals("None") && !v.parkingSlot.equals("No Slot Available")) {
            occupiedSlots.remove(v.parkingSlot);
            v.parkingSlot = "Released";
        }
    }
}