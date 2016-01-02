package ua.sumdu.greenberg.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
//import ua.sumdu.greenberg.model.Messager;
//import ua.sumdu.greenberg.model.OracleDataBase;
import ua.sumdu.greenberg.model.objects.Category;
import ua.sumdu.greenberg.model.objects.Picture;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
* This servlet working with user.jsp.
*
* Created by Greenberg Dima <gdvdima2008@yandex.ru>
*/
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
//    EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
    private static final String LOCAL_ADDRESS = "D:/NetCracker/GitHub/l4_EJB/target/AuctionEJB/images/product-images/";
//    private static final String LOCAL_ADDRESS = "D:\\GitHub\\WebApplication\\target\\WebApplication\\images\\product-images\\";
//    private static final String LOCAL_ADDRESS = "C:/Documents and Settings/User/IdeaProjects/WebApplication/target/WebApplication/images/product-images/"; // Laptop
    private static final String URL_ADDRESS = "../AuctionEJB/images/product-images/";
    private String showContent;
    private List<Category> categoryList;
    private List<Product> purchasedList;
    private List<Product> followingList;
    private List<Picture> pictures;
    private List<User> users;
    private List<Product> goods;
    private Product product;
    private List<Integer> categoryID = new ArrayList<>();
    private List<String> productURL = new ArrayList<>();
    private boolean step2;
    private boolean step3;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        showContent = "information";
//        categoryList = OracleDataBase.getInstance().getAllCategories();
//        categoryList = (List<Category>) em.createNamedQuery("GET_ALL_CATEGORIES").getResultList();
        purchasedList = null;
        followingList = null;
        pictures = null;
        users = null;
        goods = null;
        product = null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if ("clickBack".equals(request.getParameter("action"))) {
            log.info("Click exit");
            showContent = "information";
            purchasedList = null;
            followingList = null;
            pictures = null;
            users = null;
            goods = null;
            product = null;
            step2 = false;
            step3 = false;
            sendResponse(response, "<result>OK</result>");
        } else if ("clickInform".equals(request.getParameter("action"))) {
            log.info("Click Information");
            showContent = "information";
            sendResponse(response, "<result>OK</result>");
        } else if ("clickUserChangeData".equals(request.getParameter("action"))) {
            log.info("Click UserChangeData");
            showContent = "changeUserData";
            sendResponse(response, "<result>OK</result>");
        } else if ("changePassword".equals(request.getParameter("action"))) {
            showContent = "changePassword";
            sendResponse(response, "<result>OK</result>");
        } else if ("changeUser".equals(request.getParameter("action"))) {
            showContent = "changeUser";
            sendResponse(response, "<result>OK</result>");
        } else if ("changeEmail".equals(request.getParameter("action"))) {
            showContent = "changeEmail";
            sendResponse(response, "<result>OK</result>");
        } else if ("clickChangePassword".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
//                if (OracleDataBase.getInstance().changePassword(user.getId(),
//                    request.getParameter("oldPassword"), request.getParameter("newPassword"))) {
//                    sendResponse(response, "<result>OK</result>");
//                }
                if (request.getParameter("oldPassword").equals(user.getPassword())) {
                    ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                    user.setPassword(request.getParameter("newPassword"));
                    ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
                    sendResponse(response, "<result>OK</result>");
                }
            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
        } else if ("sendNewUserData".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
//                if (OracleDataBase.getInstance().changeData(user.getId(), request.getParameter("name"),
//                        request.getParameter("secondName"), request.getParameter("phone"))) {
//                    request.getSession().setAttribute("user", null);
//                    sendResponse(response, "<result>OK</result>");
//                }
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                user.setName(request.getParameter("name"));
                user.setSecondName(request.getParameter("secondName"));
                user.setPhone(request.getParameter("phone"));
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
                request.getSession().setAttribute("user", null);
                sendResponse(response, "<result>OK</result>");

            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
        } else if ("clickChangeEmail".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
//                if (Messager.changeMail(user.getLogin(), request.getParameter("checkEmail"))) {
//                    request.getSession().setAttribute("user", null);
//                    sendResponse(response, "<result>OK</result>");
//                }
            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
        } else if ("clickAddLotPage".equals(request.getParameter("action"))) {
            log.info("Click clickAddLotPage");
            showContent = "clickAddLotPage";
            sendResponse(response, "<result>OK</result>");
        } else if ("clickAddLot".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                int hours = Integer.parseInt(request.getParameter("hours"));
                int minutes = Integer.parseInt(request.getParameter("minutes"));
                if (hours >= 0 && hours < 24) {
                    if (minutes >= 0 && minutes < 60) {
                        if (Integer.parseInt(request.getParameter("startPrice")) > 0 &&
                                Integer.parseInt(request.getParameter("buyOutPrice")) > 0) {
                            if (Integer.parseInt(request.getParameter("buyOutPrice")) > Integer.parseInt(request.getParameter("startPrice"))) {
//                                int productID = OracleDataBase.getInstance().addProduct(user.getId(),
//                                        request.getParameter("title"),
//                                        request.getParameter("description"),
//                                        Long.parseLong(request.getParameter("endDate")) + hours * 3600000 + minutes * 60000,
//                                        Integer.parseInt(request.getParameter("startPrice")),
//                                        Integer.parseInt(request.getParameter("buyOutPrice")));
                                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                                Product newProduct = new Product();
                                newProduct.setSellerID(user.getId());
                                newProduct.setName(request.getParameter("title"));
                                newProduct.setDescription(request.getParameter("description"));
                                newProduct.setStartDate(new Date());
                                newProduct.setEndDate(new Date(Long.parseLong(request.getParameter("endDate")) + hours * 3600000 + minutes * 60000));
                                newProduct.setStartPrice(Integer.parseInt(request.getParameter("startPrice")));
                                newProduct.setBuyoutPrice(Integer.parseInt(request.getParameter("buyOutPrice")));
                                newProduct.setCurrentPrice(0);
                                newProduct.setActive("active");
                                ((EntityManager) request.getSession().getAttribute("em")).persist(newProduct);
                                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
                                int productID = newProduct.getId();

                                if (productID > 0) {
                                    product = null;
//                                    product = OracleDataBase.getInstance().getProduct(productID);
                                    product = (Product) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, productID).getResultList().get(0);
                                    sendResponse(response, "<result>OK</result>");
                                } else {
                                    sendResponse(response, "<result>Incorrect product number</result>");
                                }
                            } else {
                                sendResponse(response, "<result>By it now less than start price</result>");
                            }
                        } else {
                            sendResponse(response, "<result>Incorrect price.</result>");
                        }
                    } else {
                        sendResponse(response, "<result>Incorrect minutes.</result>");
                    }
                } else {
                    sendResponse(response, "<result>Incorrect hours.</result>");
                }
            }
        } else if ("showLotsPurchased".equals(request.getParameter("action"))) {
//            if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
//                purchasedList = OracleDataBase.getInstance().getUsersBuying(user.getId());
            purchasedList = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USER_BUYING").setParameter(1, user.getId()).getResultList();
            showContent = "showLotsPurchased";
            sendResponse(response, "<result>OK</result>");
//            }
        } else if ("followingProducts".equals(request.getParameter("action"))) {
//            if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
//                followingList = OracleDataBase.getInstance().getFollowingProducts(user.getId());
            followingList = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_FOLLOWING_PRODUCTS").setParameter(1, user.getId()).getResultList();
            showContent = "followingProducts";
            sendResponse(response, "<result>OK</result>");
//            }
        } else if ("clickSoldGoodsPage".equals(request.getParameter("action"))) {
//            if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
//                goods = OracleDataBase.getInstance().getUsersProducts(user.getId());
            goods = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USERS_PRODUCTS").setParameter(1, user.getId()).getResultList();
            showContent = "clickSoldGoodsPage";
            sendResponse(response, "<result>OK</result>");
//            }
        } else if ("clickGoodsForSalePage".equals(request.getParameter("action"))) {
//            if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
//                goods = OracleDataBase.getInstance().getUsersProducts(user.getId());
            goods = (List<Product>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_USERS_PRODUCTS").setParameter(1, user.getId()).getResultList();
            showContent = "clickGoodsForSalePage";
            sendResponse(response, "<result>OK</result>");
//            }
        } else if ("clickGoodsForSale".equals(request.getParameter("action"))) {
            request.getSession().setAttribute("prodID", Integer.parseInt(request.getParameter("prodID")));
            sendResponse(response, "<result>OK</result>");
        } else if ("addCategories".equals(request.getParameter("action"))) {
            categoryID.add(Integer.parseInt(request.getParameter("categoryID")));
            sendResponse(response, "<result>OK</result>");
        } else if ("clickAddCategories".equals(request.getParameter("action"))) {
//            if (OracleDataBase.getInstance().addCategoriesToProduct(Integer.parseInt(request.getParameter("productID")),
//                    categoryID)) {
                categoryList = null;
                step2 = true;
                sendResponse(response, "<result>OK</result>");
//            }


        } else if ("clickNewLot".equals(request.getParameter("action"))) {
            product = null;
            step2 = false;
            step3 = false;
            sendResponse(response, "<result>OK</result>");
        } else {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            if (uploadImage(request)) {
//                if (OracleDataBase.getInstance().addPicturesToProduct(product.getId(), productURL)) {
//                    step3 = true;
//                    response.getWriter().print("File is successfully uploaded \r\n");
//                } else {
//                    response.getWriter().print("Error: Add to pictures \r\n");
//                }

//                Picture pic = new Picture(product.getId(), productURL.get(0));
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().begin();
                ((EntityManager) request.getSession().getAttribute("em")).persist(new Picture(product.getId(), productURL.get(0)));
                ((EntityManager) request.getSession().getAttribute("em")).getTransaction().commit();
                step3 = true;
                response.getWriter().print("File is successfully uploaded \r\n");
            } else {
                response.getWriter().print("Error: File is not upload \r\n");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        categoryList = OracleDataBase.getInstance().getAllCategories();
//        pictures = OracleDataBase.getInstance().getAllPictures();
//        users = OracleDataBase.getInstance().getAllUsers();
        categoryList = (List<Category>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_CATEGORIES").getResultList();
        users = (List<User>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_USERS").getResultList();
        pictures = (List<Picture>) ((EntityManager) request.getSession().getAttribute("em")).createNamedQuery("GET_ALL_PICTURES").getResultList();


        request.setAttribute("showContent", showContent);
        request.setAttribute("categories", categoryList);
        request.setAttribute("purchasedList", purchasedList);
        request.setAttribute("followingList", followingList);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        request.setAttribute("goods", goods);
        request.setAttribute("product", product);
        request.setAttribute("step2", step2);
        request.setAttribute("step3", step3);
        RequestDispatcher rd = request.getRequestDispatcher("jsp/user.jsp");
        rd.forward(request, response);
    }


    /**
     * This method send response.
     *
     * @param response - HttpServletResponse.
     * @param text     - message.
     */
    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * This method upload image.
     *
     * @param request - image.
     * @return true or false.
     */
    private boolean uploadImage(HttpServletRequest request) {
        InputStream content = null;
        OutputStream fos = null;
        String fileName = null;
        try {

            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                fileName = item.getName();
                content = item.getInputStream();
            }

            File file = new File(LOCAL_ADDRESS + fileName);
            if (!file.exists()) {
                productURL.clear();
                productURL.add(URL_ADDRESS + fileName);
                file.createNewFile();
                fos = new FileOutputStream(file);
                int x = 0;
                byte[] buffer = new byte[256];
                while ((x = content.read(buffer)) != -1) {
                    fos.write(buffer, 0, x);
                }
            } else {
                log.info("Create new file name");
                String newFileName = randomName(fileName);
                File newFile = new File(LOCAL_ADDRESS + newFileName);
                productURL.clear();
                productURL.add(URL_ADDRESS + newFileName);
                newFile.createNewFile();
                fos = new FileOutputStream(newFile);
                int x = 0;
                byte[] buffer = new byte[256];
                while ((x = content.read(buffer)) != -1) {
                    fos.write(buffer, 0, x);
                }
            }
            fos.flush();
            fos.close();
            return true;
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method create new file name.
     *
     * @param fileName - old file name.
     * @return new file name.
     */
    private String randomName(String fileName) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        do {
            if (sb.length() > 0) {
                sb.delete(0, sb.length());
            }
            sb.append(fileName.substring(0, fileName.lastIndexOf(".")));
            sb.append(String.valueOf(random.nextInt()));
            sb.append(fileName.substring(fileName.lastIndexOf(".")));
        } while (!checkRandom(sb.toString()));
        return sb.toString();
    }

    /**
     * This method checks for file.
     *
     * @param name - name file.
     * @return true or false.
     */
    private boolean checkRandom(String name) {
        File nFile = new File(LOCAL_ADDRESS + name);
        if (!nFile.exists()) {
            return true;
        }
        return false;
    }
}
