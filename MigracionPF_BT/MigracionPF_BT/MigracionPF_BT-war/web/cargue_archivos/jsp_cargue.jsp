<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <meta charset="utf-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
        <meta name="description" content=""></meta>
        <meta name="author" content=""></meta>
        <title>Sistema de Información de Recursos de Laboratorios</title>

        <!-- Bootstrap Core CSS -->
        <link href="../css/bootstrap.min.css" rel="stylesheet"/>

        <!-- Custom CSS -->
        <link rel="stylesheet" href="../css/landing-page.css" />

        <!-- Custom Fonts -->
        <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css"/>
    </head>
    <body style="background-image: url('http://www.ucentral.edu.co/images/template_images/ucentral/fondos/fondo_template_default.jpg'); background-repeat: repeat; background-position: 0% 0%; background-attachment: fixed; background-color: #ffffff;" >
        <div class="container">
            <div class="row">
                <!-- Navigation -->
                <nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
                    <div class="container topnav">
                        <!-- Brand and toggle get grouped for better mobile display -->
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand">
                                <img alt="500x100" height="50" width="100" class="img-responsive" src="../img/logo-universidad-central.png" />
                                <a class="navbar-brand topnav" href="../#">SIRELAB</a>    
                            </a>
                        </div>
                        <!-- Collect the nav links, forms, and other content for toggling -->
                        <!-- /.navbar-collapse -->
                    </div>
                    <!-- /.container -->
                </nav>
            </div>
            <div class="row">
                <br></br>
                <br></br>
            </div>
            <div class="row">
                <div class="col-md-12 row">
                    <h2 class="info">
                        Información Registro
                    </h2>
                    <p>
                        Los campos marcados con (*) son obligatorios. Los caracteres especiales estan prohibidos para el registro de la información. 
                    </p>
                    <p>
                        Este registro es unicamente para estudiantes. 
                    </p>
                </div>
                <form action="com.sirelab.controller.ServletCargueArchivo" method="get" >
                    <div class="row col-md-12">
                        <fieldset>
                            <legend class="text-info">Datos Personales</legend>
                            <div class="form-group">
                                <strong><outputText value="Archivito"/></strong>
                                <input type="file"  name="archivo" class="form-control" id="archivo"  />
                            </div>
                        </fieldset>
                    </div>
                    <div class="right pull-right">
                        <input type="submit" value="Aceptar" name="aceptar" class="btn btn-success"/>
                    </div>
                </form>
            </div>
        </div>
        <!-- Footer -->
        <footer>
            <div class="container">
                <div class="row ">
                    <div class="col-lg-10">
                        <ul class="list-inline">
                            <li>
                                <a href="../#">Home</a>
                            </li>
                        </ul>
                        <p class="copyright text-muted small">Copyright; Your Company 2014. All Rights Reserved</p>
                    </div>
                    <div class="col-lg-2">
                        <div class="right">
                            <img alt="300x400" class="img-responsive img-rounded" src="../img/logo_sirelab.jpg" />
                        </div>
                    </div>
                </div>
            </div>
        </footer>

        <!-- jQuery -->
        <script src="../../js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="../../js/bootstrap.min.js"></script>
    </body>
</html>
