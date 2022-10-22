package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class CancelReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536594787692473L;

    private static final Logger LOG = Logger.getLogger(CancelReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.debug("Command starts");
//        User user = (User) request.getSession().getAttribute("user");
        int reportID = -1;
        String reportIdParam = request.getParameter("reportId");
        try {
            if (reportIdParam != null && !reportIdParam.isEmpty()) {
                reportID = Integer.parseInt(reportIdParam);
            }
        } catch (Exception e) {
            LOG.error("incorrect report ID:" + reportIdParam);
        }

        if (reportID >= 0) {
            LOG.trace("obtain report id: " + reportID + " => cancel current report");
            DBManager.getInstance().getReportDAO().updateReportStatus(reportID, Status.DRAFT);
        } else {
            LOG.trace("incorrect report id");
        }

        LOG.trace("Command finished");
        response.sendRedirect(Path.COMMAND_REPORT_LIST);
        return null;
    }
}