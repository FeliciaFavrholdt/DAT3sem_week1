package facades;

import dtos.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class UserFacade {
    private Connection con = null;

    private void setConnection(String db) {
        try {
            if (con == null || con.isClosed())
                con = DBConnector.connection(db);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public UserFacade(String db) {
        setConnection(db);
    }

    public UserDTO create(UserDTO user) {
        try {
            String SQL = "INSERT INTO usertable (fname, lname, pw, phone, address) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFname());
            ps.setString(2, user.getLname());
            ps.setString(3, user.getPw());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int key = rs.getInt(1);
                user.setId(key);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDTO update(UserDTO userDTO) {
        try {
            String SQL = "UPDATE usertable SET fname = ?, lname = ?, pw = ?, phone = ?, address = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, userDTO.getFname());
            ps.setString(2, userDTO.getLname());
            ps.setString(3, userDTO.getPw());
            ps.setString(4, userDTO.getPhone());
            ps.setString(5, userDTO.getAddress());
            ps.setInt(6, (int) userDTO.getId());
            ps.executeUpdate();
            return userDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public UserDTO getById(long id) {

        try {
            String SQL = "SELECT * FROM usertable WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserDTO userDTO = new UserDTO(rs.getString("fname"), rs.getString("lname"), rs.getString("pw"), rs.getString("phone"), rs.getString("address"));
                userDTO.setId(rs.getInt("id"));
                return userDTO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<UserDTO> getAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM usertable";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO userDTO = new UserDTO(rs.getString("fname"), rs.getString("lname"), rs.getString("pw"), rs.getString("phone"), rs.getString("address"));
                userDTO.setId(rs.getInt("id"));
                userDTOList.add(userDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDTOList;
    }

    public static void main(String[] args) {
        try {
            Connection con = DBConnector.connection("startcode");
            String createTable = "CREATE TABLE IF NOT EXISTS `usertable` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `fname` VARCHAR(45) NULL,\n" +
                    "  `lname` VARCHAR(45) NULL,\n" +
                    "  `pw` VARCHAR(45) NULL,\n" +
                    "  `phone` VARCHAR(45) NULL,\n" +
                    "  `address` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            con.prepareStatement(createTable).executeUpdate();
            con.prepareStatement("DELETE FROM `startcode`.`usertable`").executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        UserFacade fe = new UserFacade("startcode");
        UserDTO user = fe.create(new UserDTO("Hansi", "Hasagawa", "1234", "12345678", "Hansensvej 1"));
//        int id = user.getId();
//        fe.getAll().forEach(dto -> System.out.println(dto));
//        fe.update(new UserDTO(id, "Hans", "Hansen", "1234", "12345678", "Hansensvej 1"));
//        fe.getAll().forEach(dto -> System.out.println(dto));
    }
}

