package com.library.backend.services.trying;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminOperatorService { // AdministratorOperatorsController
    private static final Logger logger = LoggerFactory.getLogger(AdminOperatorService.class);

    private final AdminService adminService;

    public AdminOperatorService() {
        this.adminService = ServiceFactory.getService(AdminService.class);
    }

    public List<User> getAllOperators() {
        return adminService.getAllUsers();
    }

    public void removeOperator(User operator) {
        try {
            if (operator.getRole() == Role.ADMIN) {
                DialogUtils.showInfo("Error", "You can't remove administrators");
            } else {
                adminService.removeOperator(operator);
            }
        } catch (Exception e) {
            logger.error("Error occurred during operator removal", e);
        }
    }

    public void createOperator() {
        try {
            SceneLoader.load("/views/admin/createOperatorScene.fxml", "Create operator");
        } catch (Exception e) {
            logger.error("Error occurred during operator creation", e);
        }
    }
}
