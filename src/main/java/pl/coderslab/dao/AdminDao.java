package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private static final String CREATE_ADMIN_QUERY =
            "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String READ_ADMIN_QUERY =
            "SELECT * FROM admins WHERE id = ?";
    private static final String UPDATE_ADMIN_QUERY =
            "UPDATE admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?, enable = ?" +
                    "WHERE id = ?";
    private static final String DELETE_ADMIN_QUERY =
            "DELETE FROM admins WHERE id = ?";
    private static final String FIND_ALL_ADMINS_QUERY =
            "SELECT * FROM admins";
    private static final String FIND_ADMINS_BY_SUPERADMIN_QUERY =
            "SELECT * FROM admins WHERE superadmin = ?";
    private static final String FIND_ADMINS_BY_ENABLE_QUERY =
            "SELECT * FROM admins WHERE enable = ?";
    private static final String FIND_LIMITED_ADMIN_QUERY =
            "SELECT * FROM admins limit ?, ?";
    private static final String FIND_ADMINs_BY_EMAIL_QUERY =
            "SELECT * FROM admins WHERE email = ?";


    /**
     * Create admin
     *
     * @param admin
     * @return
     */
    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ADMIN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, admin.getFirst_name());
            preparedStatement.setString(2, admin.getLast_name());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setByte(5, admin.getSuperAdmin());
            preparedStatement.setByte(6, admin.getEnable());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get admin by id
     *
     * @param adminId
     * @return
     */
    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ADMIN_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    admin = getAdminFromResultSet(resultSet, admin);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return admin;

    }

    /**
     * Update admin
     *
     * @param admin
     */
    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADMIN_QUERY);
            preparedStatement.setString(1, admin.getFirst_name());
            preparedStatement.setString(2, admin.getLast_name());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setByte(5, admin.getSuperAdmin());
            preparedStatement.setByte(6, admin.getEnable());
            preparedStatement.setInt(7, admin.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Remove admin by Id
     *
     * @param adminId
     */
    public void delete(int adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            preparedStatement.setInt(1, adminId);
            preparedStatement.executeUpdate();

            boolean deleted = preparedStatement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all admins
     *
     * @return
     */
    public List<Admin> findAll() {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
            List<Admin> admins = getAdminsAsArrayList(preparedStatement);
            return admins;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get admins by email
     *
     * @param email
     * @return
     */
    public List<Admin> findAdminsByEmail(String email) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ADMINs_BY_EMAIL_QUERY);
            preparedStatement.setString(1, email);
            List<Admin> admins = getAdminsAsArrayList(preparedStatement);
            return admins;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get single admin by email
     *
     * @param email
     * @return
     */
    public Admin findSingleAdminByEmail(String email) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ADMINs_BY_EMAIL_QUERY)
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    admin = getAdminFromResultSet(resultSet, admin);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return admin;

    }

    /**
     * Get admin by superadmin
     *
     * @param superAdmin
     * @return
     */
    public List<Admin> findAdminsBySuperAdmin(byte superAdmin) {
        return getAdmins(superAdmin, FIND_ADMINS_BY_SUPERADMIN_QUERY);
    }

    /**
     * Get admins by enable
     *
     * @param enable
     * @return
     */
    public List<Admin> findAdminsByEnable(byte enable) {
        return getAdmins(enable, FIND_ADMINS_BY_ENABLE_QUERY);
    }

    private List<Admin> getAdmins(byte valueToSubstitute, String findAdminsBySuperAdminQuery) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findAdminsBySuperAdminQuery);
            preparedStatement.setByte(1, valueToSubstitute);
            List<Admin> admins = getAdminsAsArrayList(preparedStatement);
            return admins;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Admin> findSpecificNumbersOfAdmins(int startAdminsRecord, int numbersOfAdminsRecord) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_LIMITED_ADMIN_QUERY);
            preparedStatement.setInt(1, startAdminsRecord);
            preparedStatement.setInt(2, numbersOfAdminsRecord);
            List<Admin> admins = getAdminsAsArrayList(preparedStatement);
            return admins;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private List<Admin> getAdminsAsArrayList(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Admin> admins = new ArrayList<>();
        while ((resultSet.next())) {
            Admin admin = new Admin();
            admin = getAdminFromResultSet(resultSet, admin);
            admins.add(admin);
        }
        return admins;
    }

    private Admin getAdminFromResultSet(ResultSet resultSet, Admin admin) throws SQLException {
        admin.setId(resultSet.getInt("id"));
        admin.setFirst_name(resultSet.getString("first_name"));
        admin.setLast_name(resultSet.getString("last_name"));
        admin.setEmail(resultSet.getString("email"));
        admin.setHashPassword(resultSet.getString("password"));
        admin.setSuperAdmin(resultSet.getByte("superadmin"));
        admin.setEnable(resultSet.getByte("enable"));
        return admin;
    }

    public boolean authorisation(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public boolean isAuthorised(String password, String email) {
        AdminDao adminDao = new AdminDao();
        List<Admin> admins = adminDao.findAdminsByEmail(email);
        if (admins.size() == 0) {
            return false;
        }
        String hashedPassword = admins.get(0).getPassword();
        if (adminDao.authorisation(password, hashedPassword)) {
            return true;
        }
        return false;
    }

}
//TODO sprawdzic poprawnosc metod pod katem CLOSEBLE