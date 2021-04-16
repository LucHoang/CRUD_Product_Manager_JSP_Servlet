<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->
    <style>
        a:hover {
            color: white;
            text-decoration: none;
        }
        .button {
            background-color: #4CAF50; /* Green */
            border: none;
            border-radius: 5px;
            color: white;
            padding: 6px 8px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 15px;
            margin: 4px 2px;
            cursor: pointer;
            -webkit-transition-duration: 0.4s; /* Safari */
            transition-duration: 0.4s;
        }

        /*.button1 {*/
        /*  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);*/
        /*}*/

        .button:hover {
            box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
        }
    </style>
    <title>CRUD | Hoàng Sỹ Lực</title>
</head>
<body>
<form class="form-horizontal" method="post">
    <fieldset>

        <!-- Form Name -->
        <div style="margin-top: auto">
            <legend style="margin-top: 40px; text-align: center">EDIT PRODUCT</legend>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="product_name">Name</label>
            <div class="col-md-4">
                <input id="product_name" name="name" class="form-control input-md" required="" type="text" VALUE="${product.getName()}">

            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="product_name_fr">Price</label>
            <div class="col-md-4">
                <input id="product_name_fr" name="price" class="form-control input-md" required="" type="text" VALUE="${product.getPrice()}">
            </div>
        </div>

        <!-- Select Basic -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="product_categorie">Quantity</label>
            <div class="col-md-4">
                <%--                <select id="product_categorie" name="product_categorie" class="form-control">--%>
                <%--                </select>--%>
                <input id="product_categorie" name="quantity" class="form-control" required="" type="text" VALUE="${product.getQuantity()}">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="available_quantity">Color</label>
            <div class="col-md-4">
                <input id="available_quantity" name="color" class="form-control input-md" required="" type="text" VALUE="${product.getColor()}">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="product_weight">Description</label>
            <div class="col-md-4">
                <input id="product_weight" name="description" class="form-control input-md" required="" type="text" VALUE="${product.getDescription()}">

            </div>
        </div>

        <!-- Textarea -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="product_description">Category</label>
            <div class="col-md-4">
<%--                                <input class="form-control" id="category" name="category" value="${category.getName()}">--%>
                <select id="category" name="category" class="form-control">
                    <c:forEach items='${requestScope["category"]}' var="category">
                        <option>${category.getName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton">Action</label>
            <div class="col-md-4">
                <button class="button button">Update</button>
                <a href="product?action=view" class="button button" style="background-color: #666666">Back</a>
                <%--                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">Button</button>--%>
            </div>
        </div>

    </fieldset>
</form>

</body>
</html>
