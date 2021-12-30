package fr.lernejo.navy_battle;



public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {System.out.println("Please enter a port  !"); System.exit(1);}
        try{
        final int port = Integer.parseInt(args[0]);
        System.out.println("listening on http://localhost:" + port + "/");
        var serv = new Serveur(port);
        if (args.length > 1)
            new Request().postRequest(port, args[1]);
        } catch (Exception e){throw new NumberFormatException(e.getMessage());}

    }
}
