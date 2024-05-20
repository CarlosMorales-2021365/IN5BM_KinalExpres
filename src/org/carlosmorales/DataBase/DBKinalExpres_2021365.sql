drop database DBKinalExpres_2021365;

create database DBKinalExpres_2021365;

set global time_zone ='-6:00';


use DBKinalExpres_2021365;

create table Clientes(
clienteID int not null,
nombreCliente varchar(50),
apellidoCliente varchar (50),
clienteNit varchar(10),
telefonoCliente varchar(8),
direccionCliente varchar(150),
correoCliente varchar(45),
primary key PK_clienteID(clienteID)
);


create table Proveedores(
proveedorID int not null,
nombresProveedor varchar(60),
apellidosProveedor varchar(60),
nitProveedor varchar(10),
direccionProveedor varchar (150),
razonSocial varchar(60),
contactoPrincipal varchar(100),
paginaWeb varchar(50),
primary key proveedorID(proveedorID)
); 


create table tipoProducto(
tipoProductoID int not null,
descripcion varchar(45),
primary key tipoProductoID(tipoProductoID)
);


create table Compras(
numeroDocumento int not null,
fechaDocumento date,
descripcion varchar(60),
totalDocumento decimal(10,2),
primary key PK_numeroDocumento(numeroDocumento)
);


create table cargoEmpleado(
cargoEmpleadoID int not null,
nombreCargo varchar(45),
descripocionCargo varchar(45),
primary key PK_cargoEmpleadoID(cargoEmpleadoID)
);

create table Productos(
	productoID varchar(15) not null,
    descripcionProducto varchar(45),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
    tipoProductoID int,
    proveedorID int,
    primary key productoID (productoID),
    constraint FK_Productos_tipoProducto foreign key Productos(tipoProductoID) 
    references tipoProducto(tipoProductoID) on delete cascade,
    constraint FK_Productos_Proveedores foreign key Productos(proveedorID) 
    references Proveedores(proveedorID) on delete cascade
);

create table DetalleCompra(
	detalleCompraID int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    productoID varchar(15),
    numeroDocumento int,
    primary key detalleCompraID (detalleCompraID),
    constraint FK_DetalleCompra_Productos foreign key DetalleCompra(productoID) 
    references Productos(productoID) on delete cascade,
    constraint FK_DetalleCompra_Compras foreign key DetalleCompra(numeroDocumento) 
    references Compras(numeroDocumento) on delete cascade
);


create table Empleados
(
	empleadoID int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal (10,2),
    direccion varchar (150),
    turno varchar(15),
    cargoEmpleadoID int,
    primary key empleadoID (empleadoID),
    constraint FK_Empleados_cargoEmpleado foreign key Empleados(cargoEmpleadoID) 
    references cargoEmpleado (cargoEmpleadoID) on delete cascade
);



-- Crud Clientes
delimiter $$
	create procedure sp_AgregarClientes(in CliID int, in nomCliente varchar(50), in apeCliente varchar (50), in cliNit varchar(10), in telCliente varchar(8), in direccionCli varchar(150),in correoCli varchar(45))
    begin
		insert into Clientes (Clientes.clienteID,Clientes.nombreCliente,Clientes.apellidoCliente,Clientes.clienteNit,Clientes.telefonoCliente,Clientes.direccionCliente,Clientes.correoCliente)
         values(CliID,nomCliente,apeCliente,cliNit,telCliente,direccionCli,correoCli );
	end $$
delimiter ;

call sp_AgregarClientes(1,'Pedro Pablo','Morales Sanches','256987415','21569283','Ciudad capital','PSAnches@kinal.edu.gt');
call sp_AgregarClientes(2,'Juan Armando','Gonzalez Gonzalez','123456789','90657085','Ciudad capital','Juan@gmail.com');



delimiter $$
create procedure sp_MostrarClientes ()
begin 
	select
    c.clienteID,
    c.nombreCliente,
    c.apellidoCliente,
    c.clienteNit,
    c.telefonoCliente,
    c.direccionCliente,
    c.correoCliente
    from clientes c;
end $$        
delimiter ;
call sp_MostrarClientes;

delimiter $$
create procedure sp_buscarClientes (in CliID int)
begin 
	select* from Clientes where Clientes.clienteID = CliID;
end $$        
delimiter ;
call sp_buscarClientes(1);


delimiter $$
create procedure sp_ActualizarCliente (in CliID int, in nomCliente varchar(50), in apeCliente varchar (50), in cliNit varchar(10), in telCliente varchar(8), in direccionCli varchar(150),in correoCli varchar(45))
begin 
	update Clientes
	set
        nombreCliente = nomCliente,
        apellidoCliente = apeCliente,
        clienteNit = cliNit,
        telefonoCliente = telCliente,
        direccionCliente = direccionCli,
        correoCliente = correoCli
    where 
		clienteID = CliID;
end $$        
delimiter ;
call sp_ActualizarCliente(1,'Pedro Pablo','Morales Sanches','256987415','21569283','Ciudad capital','PSanches@kinal.edu.gt');



delimiter $$
create procedure sp_EliminarCliente (in CliID int)
begin 
	delete from Clientes
   where Clientes.clienteID = CliID;
end $$        
delimiter ;
call sp_EliminarCliente(3);





-- Crud Proveedor

delimiter $$
	create procedure sp_AgregarProveedores(in provID int, nombProveedor varchar(60), in apeProveedor varchar(60), in nitProv varchar(10),direccionProv varchar (150), razSoc varchar(60), in contactoPrin varchar(100),in pagWeb varchar(50))
    begin
		insert into Proveedores (Proveedores.proveedorID ,Proveedores.nombresProveedor,Proveedores.apellidosProveedor,Proveedores.nitProveedor,Proveedores.direccionProveedor,Proveedores.razonSocial,Proveedores.contactoPrincipal,Proveedores.paginaWeb)
         values(provID,nombProveedor,apeProveedor,nitProv,direccionProv,razSoc,contactoPrin,pagWeb);
	end $$
delimiter ;
call sp_AgregarProveedores(1,'Carlos','Estuardo','1234334134','ciudad capital','','tel: 23456098','www.CEProductosparaTodo');

delimiter $$
create procedure sp_MostrarProveedores ()
begin 
	select
    p.proveedorID ,
    p.nombresProveedor,
    p.apellidosProveedor,
    p.nitProveedor,
    p.direccionProveedor,
    p.razonSocial,
    p.contactoPrincipal,
    p.paginaWeb
    from Proveedores p;
end $$        
delimiter ;
call sp_MostrarProveedores();

delimiter $$
create procedure sp_buscarProveedor (in provID int)
begin 
	select* from Proveedores where Proveedores.proveedorID = provID;
end $$        
delimiter ;
call sp_buscarProveedor(1);


delimiter $$
create procedure sp_ActualizarProveedor (in provID int, nombProveedor varchar(60), in apeProveedor varchar(60), in nitProv varchar(10),direccionProv varchar (150), razSoc varchar(60), in contactoPrin varchar(100),in pagWeb varchar(50))
begin 
	update Proveedores
	set
    nombresProveedor = nombProveedor,
    apellidosProveedor = apeProveedor,
    nitProveedor = nitProv,
    direccionProveedor = direccionProv,
    razonSocial = razSoc,
    contactoPrincipal = contactoPrin,
    paginaWeb = pagWeb
    where 
		proveedorID = provID;
end $$        
delimiter ;
call sp_ActualizarProveedor (1,'Carlos','Estuardo','1234334134','ciudad capital','Germalidos','tel: 23480098','www.CEProductosparaTodo');


delimiter $$
create procedure sp_EliminarProveedor (in provID int)
begin 
	delete from Proveedores
   where Proveedores.proveedorID = provID;
end $$        
delimiter ;
call sp_EliminarProveedor(3);


-- curd tipo Producto



delimiter $$
	create procedure sp_AgregartipoProducto(in tipoProdID int, descrip varchar(45))
    begin
		insert into tipoProducto (tipoProducto.tipoProductoID,tipoProducto.descripcion)
         values(tipoProdID,descrip);
	end $$
delimiter ;
call sp_AgregartipoProducto(1,'productos de higiene perrsonal');
call sp_AgregartipoProducto(2,'productos Lacteos');

delimiter $$
create procedure sp_MostrarTipoProducto ()
begin 
	select
    t.tipoProductoID,
    t.descripcion
    from tipoProducto t;
end $$        
delimiter ;
call sp_MostrarTipoProducto();



delimiter $$
create procedure sp_buscarTipoProducto (in tipoProdID int)
begin 
	select* from tipoProducto where tipoProducto.tipoProductoID=tipoProdID;
end $$        
delimiter ;
call sp_buscarTipoProducto(1);


delimiter $$
create procedure sp_ActualizarTipoProducto (in tipoProdID int, descrip varchar(45))
begin 
	update tipoProducto
	set
    descripcion = descrip
    where 
	tipoProductoID = tipoProdID	;
end $$        
delimiter ;
call sp_ActualizarTipoProducto(1,'productos de higiene personal');


delimiter $$
create procedure sp_EliminarTipoProducto (in tipoProdID int)
begin 
	delete from tipoProducto
   where tipoProducto.tipoProductoID=tipoProdID;
end $$        
delimiter ;
call sp_EliminarTipoProducto(3);



-- crud compras 


delimiter $$
	create procedure sp_AgregarCompra(in numeroDoc int, in fechaDoc date,in descrip varchar(60),in totalDoc decimal(10,2))
    begin
		insert into Compras (Compras.numeroDocumento,Compras.fechaDocumento,Compras.descripcion,Compras.totalDocumento)
         values(numeroDoc,fechaDoc,descrip,totalDoc );
	end $$
delimiter ;
call sp_AgregarCompra(1,'05/10/024','Compra de articulos de cosina','200.00');

delimiter $$
create procedure sp_MostrarCompra ()
begin 
	select
   c.numeroDocumento,
   c.fechaDocumento,
   c.descripcion,
   c.totalDocumento
    from Compras c;
end $$        
delimiter ;
call sp_MostrarCompra();



delimiter $$
create procedure sp_buscarCompra (in numeroDoc int)
begin 
	select* from Compras where Compras.numeroDocumento=numeroDoc;
end $$        
delimiter ;
call sp_buscarCompra(1);


delimiter $$
create procedure sp_ActualizarCompra (in numeroDoc int,in fechaDoc date, in descrip varchar(60),in totalDoc decimal(10,2))
begin 
	update Compras
	set
    fechaDocumento = fechaDoc,
    descripcion = descrip,
    totalDocumento = totalDoc
    where 
	numeroDocumento=numeroDoc;
end $$        
delimiter ;
call sp_ActualizarCompra(1,'05/10/024','Compra de articulos de cosina','200.00');


delimiter $$
create procedure sp_EliminarCompra (in numeroDoc int)
begin 
	delete from Compras
   where Compras.numeroDocumento=numeroDoc;
end $$        
delimiter ;
call sp_EliminarCompra(3);


-- crud cargo empleado



delimiter $$
	create procedure sp_AgregarCargo(in cargoEmpID int, in nomCargo varchar(45), descripCargo varchar(45))
    begin
		insert into cargoEmpleado (cargoEmpleado.cargoEmpleadoID, cargoEmpleado.nombreCargo,cargoEmpleado.descripocionCargo)
         values(cargoEmpID,nomCargo,descripCargo);
	end $$
delimiter ;
call sp_AgregarCargo(1,'Genrente','administra la oficina');

delimiter $$
create procedure sp_MostrarCargo ()
begin 
	select
   ce.cargoEmpleadoID,
   ce.nombreCargo,
   ce.descripocionCargo
    from cargoEmpleado ce;
end $$        
delimiter ;
call sp_MostrarCargo ();



delimiter $$
create procedure sp_buscarCargo (in cargoEmpID int)
begin 
	select* from cargoEmpleado where cargoEmpleado.cargoEmpleadoID=cargoEmpID;
end $$        
delimiter ;
call sp_buscarCargo(1);


delimiter $$
create procedure sp_ActualizarCargo (in cargoEmpID int, in nomCargo varchar(45), descripCargo varchar(45))
begin 
	update cargoEmpleado
	set
    nombreCargo=nomCargo,
    descripocionCargo=descripCargo
    where 
	cargoEmpleadoID=cargoEmpID;
end $$        
delimiter ;
call sp_ActualizarCargo(1,'Genrente','administra la oficina');


delimiter $$
create procedure sp_EliminarCargo (in cargoEmpID int)
begin 
	delete from cargoEmpleado
   where cargoEmpleado.cargoEmpleadoID=cargoEmpID;
end $$        
delimiter ;
call sp_EliminarCargo(3);



-- crud Productos 



delimiter $$
	create procedure sp_AgregarProducto(in prodID varchar(15), in descripProducto varchar(45), in precioU decimal(10,2), in precioD decimal(10,2), in precioM decimal(10,2), in exis int, in  tipoProdID int,in  provID int)
    begin
		insert into Productos (Productos.productoID,Productos.descripcionProducto,Productos.precioUnitario,Productos.precioDocena,Productos.precioMayor,Productos.existencia,Productos.tipoProductoID,Productos.proveedorID)
         values(prodID,descripProducto ,precioU,precioD,precioM,exis,tipoProdID, provID);
	end $$
delimiter ;
call sp_AgregarProducto(1,'Leche Entera','12.00','270.00','900.00',17,2,1);

delimiter $$
create procedure sp_MostrarProductos ()
begin 
	select
   prod.productoID,
   prod.descripcionProducto,
   prod.precioUnitario,
   prod.precioDocena,
   prod.precioMayor,
   prod.existencia,
   prod.tipoProductoID,
   prod.proveedorID
    from Productos Prod;
end $$        
delimiter ;
call sp_MostrarProductos ();



delimiter $$
create procedure sp_buscarProducto (in prodID varchar(15))
begin 
	select* from Productos where Productos.productoID=prodID;
end $$        
delimiter ;
call sp_buscarProducto(1);


delimiter $$
create procedure sp_ActualizarProducto (in prodID varchar(15), in descripProducto varchar(45), in precioU decimal(10,2), in precioD decimal(10,2), in precioM decimal(10,2), in exis int, in  tipoProdID int,in  provID int)
begin 
	update Productos
	set
    descripcionProducto=descripProducto,
    precioUnitario=precioU,
    precioDocena=precioD,
    precioMayor=precioM,
    existencia=exis,
    tipoProductoID=tipoProdID,
    proveedorID=provID
    where 
	productoID=prodID;
end $$        
delimiter ;
call sp_ActualizarProducto(1,'Leche Entera','12.00','270.00','880.00',17,2,1);


delimiter $$
create procedure sp_EliminarProducto (in prodID varchar(15))
begin 
	delete from Productos
   where Productos.productoID=prodID;
end $$        
delimiter ;
call sp_EliminarProducto(3);



-- crud Detelle Compra



delimiter $$
	create procedure sp_AgregarDetalleCompra(in detalleComID int,in costoUni decimal(10,2),in cant int, in prodID varchar(15),in numDocumento int)
    begin
		insert into DetalleCompra (DetalleCompra.detalleCompraID,DetalleCompra.costoUnitario,DetalleCompra.cantidad,DetalleCompra.productoID,DetalleCompra.numeroDocumento)
         values(detalleComID,costoUni,cant,prodID,numDocumento);
	end $$
delimiter ;
call sp_AgregarDetalleCompra(1,'12.00',3,'1',1);

delimiter $$
create procedure sp_MostrarDetallesCompras ()
begin 
	select
   dc.detalleCompraID,
   dc.costoUnitario,
   dc.cantidad,
   dc.productoID,
   dc.numeroDocumento
    from DetalleCompra dc;
end $$        
delimiter ;
call sp_MostrarDetallesCompras ();



delimiter $$
create procedure sp_buscarDetalleCompra (in detalleComID int)
begin 
	select* from DetalleCompra where DetalleCompra.detalleCompraID=detalleComID;
end $$        
delimiter ;
call sp_buscarDetalleCompra(1);


delimiter $$
create procedure sp_ActualizarDetalleCompra (in detalleComID int,in costoUni decimal(10,2),in cant int, in prodID varchar(15),in numDocumento int)
begin 
	update DetalleCompra
	set
   costoUnitario=costoUni,
   cantidad=cant,
   productoID=prodID,
   numeroDocumento=numDocumento
    where 
	detalleCompraID=detalleComID;
end $$        
delimiter ;
call sp_ActualizarDetalleCompra(1,'12.00',3,'1',1);


delimiter $$
create procedure sp_EliminarDetalleCompra (in detalleComID int)
begin 
	delete from DetalleCompra
   where DetalleCompra.detalleCompraID=detalleComID;
end $$        
delimiter ;
call sp_EliminarDetalleCompra(3);


-- crud Empleados


create table Empleados
(
	empleadoID int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal (10,2),
    direccion varchar (150),
    turno varchar(15),
    cargoEmpleadoID int,
    primary key empleadoID (empleadoID),
    constraint FK_Empleados_cargoEmpleado foreign key Empleados(cargoEmpleadoID) 
    references cargoEmpleado (cargoEmpleadoID) on delete cascade
);


delimiter $$
	create procedure sp_AgregarEmpleado(in empID int, in nomEmpleado varchar(50), in apeEmpleado varchar(50), in suel decimal (10,2), in  direc varchar (150),in turn varchar(15), in cargoEmpID int)
    begin
		insert into Empleados (Empleados.empleadoID, Empleados.nombresEmpleado, Empleados.apellidosEmpleado,Empleados.sueldo,Empleados.direccion,Empleados.turno,Empleados.cargoEmpleadoID)
         values(empID,nomEmpleado,apeEmpleado,suel,direc,turn,cargoEmpID);
	end $$
delimiter ;
call sp_AgregarEmpleado(1,'Erick','Garcia','10000.00','29 calle 15-76 zona 5','1 Matutino',1);

delimiter $$
create procedure sp_MostrarEmpleados ()
begin 
	select
   e.empleadoID,
   e.nombresEmpleado,
   e.apellidosEmpleado,
   e.sueldo,
   e.direccion,
   e.turno,
   e.cargoEmpleadoID
    from Empleados e;
end $$        
delimiter ;
call sp_MostrarEmpleados ();



delimiter $$
create procedure sp_buscarEmpleado (in empID int)
begin 
	select* from Empleados where Empleados.empleadoID=empID;
end $$        
delimiter ;
call sp_buscarEmpleado(1);


delimiter $$
create procedure sp_ActualizarEmpleado (in empID int, in nomEmpleado varchar(50), in apeEmpleado varchar(50), in suel decimal (10,2), in  direc varchar (150),in turn varchar(15), in cargoEmpID int)
begin 
	update Empleados
	set
   nombresEmpleado=nomEmpleado,
   apellidosEmpleado=apeEmpleado,
   sueldo=suel,
   direccion=direc,
   turno=turn,
   cargoEmpleadoID=cargoEmpID
    where 
	empleadoID=empID;
end $$        
delimiter ;
call sp_ActualizarEmpleado(1,'Erick','Garcia','10000.00','29 calle 15-76 zona 5','1 Matutino',1);


delimiter $$
create procedure sp_EliminarEmpleado (in empID int)
begin 
	delete from Empleados
   where Empleados.empleadoID=empID;
end $$        
delimiter ;
call sp_EliminarEmpleado(3);