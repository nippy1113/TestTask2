import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Launcher {
 private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 private String launchInputFile;
 private String launchOperationType;
 private String launchOutputFile;

    public void launchProgramm() throws IOException {
        try {
            while (true) {
                System.out.println("Input the InputFilePAth:");
                launchInputFile = reader.readLine();
                if (launchInputFile.equals("stop")) {
                    break;
                }
                System.out.println("Input the operation Type:");
                launchOperationType = reader.readLine();
                System.out.println("Input the OutputFilePath:");
                launchOutputFile = reader.readLine();
                new DataBaseService().createDataBase(launchInputFile, launchOperationType, launchOutputFile);
                System.out.println("DataBase created! (you can input \"stop\" to stop the program)");
            }
        }catch(FileNotFoundException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(launchOutputFile, new ErrorClass().exceptionJson(e));
        }
        catch (IOException e) {
            System.out.println("Wrong input!");
            launchProgramm();
        } catch (ParseException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(launchOutputFile, new ErrorClass().exceptionJson(e));
        } catch (java.text.ParseException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(launchOutputFile, new ErrorClass().exceptionJson(e));
        } catch (SQLException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(launchOutputFile, new ErrorClass().exceptionJson(e));
        }
    }
}
