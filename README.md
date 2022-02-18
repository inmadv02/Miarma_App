# Miarma_App

***MiarmaApp** es una red social para sevillanos, donde pueden subir fotos y vídeos de su querida ciudad.*
> ### ✒️ **Autor ✒️**
* #### Mª Inmaculada Domínguez Vargas

### 📋 Las entidades que forman nuestra aplicación 📋
* #### Usuario 🧍
* #### Post :mega: 
* #### FollowRequest ↗️

## Pasos previos para poder ejecutar el proyecto 
* #### **Descargar Maven**
* #### **Descargar JAVA JDK 17**
* #### **Abrir nuestro IDE**
* #### **Configurar la version JDK**
#### **Y finalmente...ejecutar el proyecto**


## 🛠️ ¿Qué puede hacer MiarmaApp? 🛠️


### **Registro y autenticación**

1. El usuario podrá registrarse con su correo, su nombre completo, email, un nickname, etc.
2. El usuario podrá hacer login para poder entrar a la aplicación.
3. Asimismo, podrá ver su perfil.


### **Funcionalidades de Posts:**

1. Crear una nueva publicación: con un título, un texto, y un fichero adjunto (imagen o vídeo). Un usuario podrá elegir si esa publicación es pública (todo el mundo la puede ver), o privada (solo la ven sus seguidores)
2. Editar lo que se quiera de una publicación.
3. Eliminar una publicación, eliminando a su vez el fichero adjunto.
4. Obtener todas las publicaciones públicas de todos los usuarios.
5. Obtener una publicación a través de su ID. Si seguimos a ese usuario, o si ese post es público, lo podremos ver.
6. Obtener todas las publicaciones de un usuario. Si no seguimos al usuario, solamente podremos ver las publicaciones guardadas como públicas. Si seguimos al usuario, podremos ver todas las publicaciones.
7. Obtener todas nuestras peticiones.

### **Funcionalidades Usuarios:**

1. Visualizar el perfil de un usuario. Si seguimos al usuario, o tiene su perfil como público, se obtendrá su información. En caso contrario, no podremos realizar esa funcionalidad.
2. Editar mi perfil de usuario. Se puede cambiar cualquier información, incluida la foto de avatar.
3. Realizar una petición de seguimiento del usuario a través de un nick.
4. Aceptar una petición buscándola por su ID.
5. Eliminar una petición de seguimiento.
6. Listar todas las peticiones de seguimiento que existen.

## **Cómo usar la colección de Postman correctamente**

- Importar la colección de Postman que hay en el proyecto(Miarma_App.postman_collection)
- Hacer uso de los ficheros en la carpeta "Archivos" del proyecto para realizar las peticiones que requieran un archivo multimedia(Registro, creación de post, editar un post, etc.)
- En la colección hay una variable {{token}} para la autenticación de los usuarios, por lo que no es conveniente tocar esa variable. En caso de hacerlo, volver a poner en Authorization la variable.
![image](https://user-images.githubusercontent.com/74898704/154687163-987f595d-dca5-4c2a-8d82-f6b2b259b395.png)
