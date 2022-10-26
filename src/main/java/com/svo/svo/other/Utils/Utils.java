package com.svo.svo.other.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    public static boolean isPresent(Object o) {
        return o != null && StringUtils.isNotBlank(String.valueOf(o));
    }

    public static boolean isObjectValid(Object o) {
        boolean valid = true;
        if (o == null) {
            valid = false;
        } else if (o instanceof String) {
            valid = StringUtils.isNotBlank((String)o);
        } else if (o instanceof Map) {
            valid = !((Map)o).isEmpty();
        } else if (o instanceof List) {
            valid = !((List)o).isEmpty();
        }

        return valid;
    }

    public static void validate404NotFound(Object obj, String msg) throws AppException404NotFound {
        if (!isObjectValid(obj)) {
            throw new AppException404NotFound(msg);
        }
    }

    public static void validate400BadRequest(Object obj, String msg) throws AppException400BadRequest {
        if (!isObjectValid(obj)) {
            throw new AppException400BadRequest(msg + " (requerido)");
        }
    }

    public static void validateMulti400BadRequest(Object... args) throws AppException400BadRequest {
        List<String> invalids = new ArrayList();

        for(int i = 0; i < args.length; i += 2) {
            if (!isObjectValid(args[i])) {
                invalids.add(args[i + 1] + " (requerido)");
            }
        }

        if (!invalids.isEmpty()) {
            throw new AppException400BadRequest(String.join("<br/>", invalids));
        }
    }

    public static void validateComplex400BadRequest(Object... args) throws AppException400BadRequest {
        List<String> invalids = new ArrayList();
        String sVal = null;
        double doubleVal = 0.0D;
        long longVal = 0L;
        int intVal = 0;

        for(int i = 0; i < args.length; i += 7) {
            Object value = args[i];
            Class clazz = (Class)args[i + 1];
            Boolean required = args[i + 2] != null ? (Boolean)args[i + 2] : null;
            Object min = args[i + 3];
            Object max = args[i + 4];
            Set<String> collec = args[i + 5] != null ? (HashSet)args[i + 5] : null;
            String name = (String)args[i + 6];
            boolean present = isPresent(value);
            if (Boolean.TRUE.equals(required) && !present) {
                invalids.add(name + " (requerido)");
            }

            boolean format = false;
            if (present) {
                sVal = String.valueOf(value);

                try {
                    if (clazz.equals(Double.class)) {
                        doubleVal = Double.parseDouble(sVal);
                    } else if (clazz.equals(Long.class)) {
                        longVal = Long.parseLong(sVal);
                    } else if (clazz.equals(Integer.class)) {
                        intVal = Integer.parseInt(sVal);
                    }

                    format = true;
                } catch (Exception var19) {
                    LOG.warn("El valor [{}] no es [{}]", sVal, clazz.getSimpleName());
                    invalids.add(name + " (formato)");
                }
            }

            if (present && format) {
                if (min != null) {
                    if (min instanceof String) {
                        if (sVal.length() < Integer.parseInt((String)min)) {
                            invalids.add(name + " (longitud mínima: " + (String)min + ")");
                        }
                    } else if (clazz.equals(Double.class)) {
                        if (doubleVal < (Double)min) {
                            invalids.add(name + " (valor mínimo: " + min + ")");
                        }
                    } else if (clazz.equals(Long.class)) {
                        if (longVal < (Long)min) {
                            invalids.add(name + " (valor mínimo: " + min + ")");
                        }
                    } else if (clazz.equals(Integer.class)) {
                        if (intVal < (Integer)min) {
                            invalids.add(name + " (valor mínimo: " + min + ")");
                        }
                    } else if (clazz.equals(String.class) && sVal.length() < (Integer)min) {
                        invalids.add(name + " (longitud mínima: " + min + ")");
                    }
                }

                if (max != null) {
                    if (max instanceof String) {
                        if (sVal.length() > Integer.parseInt((String)max)) {
                            invalids.add(name + " (longitud máxima: " + (String)max + ")");
                        }
                    } else if (clazz.equals(Double.class)) {
                        if (doubleVal > (Double)max) {
                            invalids.add(name + " (valor máximo: " + max + ")");
                        }
                    } else if (clazz.equals(Long.class)) {
                        if (longVal > (Long)max) {
                            invalids.add(name + " (valor máximo: " + max + ")");
                        }
                    } else if (clazz.equals(Integer.class)) {
                        if (intVal > (Integer)max) {
                            invalids.add(name + " (valor máximo: " + max + ")");
                        }
                    } else if (clazz.equals(String.class) && sVal.length() > (Integer)max) {
                        invalids.add(name + " (longitud máxima: " + max + ")");
                    }
                }

                if (collec != null && !collec.contains(sVal)) {
                    invalids.add(name + " (valor incorrecto)");
                }
            }
        }

        if (!invalids.isEmpty()) {
            throw new AppException400BadRequest(String.join("<br/>", invalids));
        }
    }

    public static String join(Object... args) {
        StringBuilder sb = new StringBuilder();
        Object[] var2 = args;
        int var3 = args.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object arg = var2[var4];
            sb.append(arg);
        }

        return sb.toString();
    }

    public static String log(Object... args) {
        StringBuilder sb = new StringBuilder();
        Object[] var2 = args;
        int var3 = args.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object arg = var2[var4];

            try {
                if (arg instanceof List) {
                    sb.append("<size ").append(((List)arg).size()).append(">");
                }

                if (arg == null) {
                    sb.append("null");
                } else {
                    String str = arg.toString();
                    if (str.length() > 1000) {
                        sb.append(str.substring(0, 1000)).append("...");
                    } else {
                        sb.append(arg);
                    }
                }
            } catch (Exception var7) {
                LoggerFactory.getLogger(Utils.class).error(var7.getMessage(), var7);
                sb.append(arg);
            }
        }

        return sb.toString();
    }

    public static void raise(Exception e, String msg) throws AppException {
        if (e instanceof AppException) {
            if (!((AppException)e).isLogged()) {
                LOG.error("APP-EXCEPTION {}: {}", e.getClass().getSimpleName(), e.getMessage());
                ((AppException)e).setLogged(true);
            }

            throw (AppException)e;
        } else if (StringUtils.isNotBlank(msg)) {
            LOG.error(msg, e);
            throw new AppException500InternalServerError(msg, e, true);
        } else {
            LOG.error("Without message", e);
            throw new AppException500InternalServerError("-nomessage-", e, true);
        }
    }

    public static <T> ResponseEntity<ResponseBody<T>> handle(Exception e, String msg) {
        ResponseEntity<ResponseBody<T>> response = null;
        ResponseBody<T> body = null;
        HttpStatus status = null;
        if (e instanceof AppException && !((AppException)e).isLogged()) {
            LOG.error("APP-EXCEPTION {}: {}", e.getClass().getSimpleName(), e.getMessage());
        }

        if (e instanceof AppException400BadRequest) {
            body = new ResponseBody(false, "Revisar los datos:<br/>" + e.getMessage().toLowerCase(), e.getMessage().toLowerCase().split("<br/>"));
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof AppException401Unauthorized) {
            body = new ResponseBody(false, "Usuario no autenticado");
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof AppException403Forbidden) {
            body = new ResponseBody(false, "Permiso denegado");
            status = HttpStatus.FORBIDDEN;
        } else if (e instanceof AppException404NotFound) {
            body = new ResponseBody(false, e.getMessage());
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof AppException500InternalServerError) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            if ("-nomessage-".equals(e.getMessage())) {
                body = new ResponseBody(false, "Error de sistema", e);
            } else {
                body = new ResponseBody(false, "Error en el proceso: " + e.getMessage().toLowerCase(), e);
            }
        } else if (e instanceof AppException) {
            body = new ResponseBody(false, e.getMessage());
            status = HttpStatus.OK;
        }

        if (status != null) {
            response = new ResponseEntity(body, status);
        } else {
            try {
                raise(e, msg);
            } catch (Exception var6) {
                response = handle(var6, msg);
            }
        }

        return response;
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK() {
        return response(HttpStatus.OK, (String)null, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(String msg) {
        return response(HttpStatus.OK, msg, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(T data) {
        return response(HttpStatus.OK, (String)null, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(T data, int total) {
        return response(HttpStatus.OK, (String)null, data, total);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(String msg, T data) {
        return response(HttpStatus.OK, msg, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(String msg, T data, int total) {
        return response(HttpStatus.OK, msg, data, total);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(boolean success) {
        return response(HttpStatus.OK, success, (String)null, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(boolean success, String msg) {
        return response(HttpStatus.OK, success, msg, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(boolean success, T data) {
        return response(HttpStatus.OK, success, (String)null, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response200OK(boolean success, String msg, T data) {
        return response(HttpStatus.OK, success, msg, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response201Created() {
        return response(HttpStatus.CREATED, (String)null, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response201Created(String msg) {
        return response(HttpStatus.CREATED, msg, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response201Created(T data) {
        return response(HttpStatus.CREATED, (String)null, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response201Created(String msg, T data) {
        return response(HttpStatus.CREATED, msg, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response202Accepted() {
        return response(HttpStatus.ACCEPTED, (String)null, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response202Accepted(String msg) {
        return response(HttpStatus.ACCEPTED, msg, (T)null);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response202Accepted(T data) {
        return response(HttpStatus.ACCEPTED, (String)null, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response202Accepted(String msg, T data) {
        return response(HttpStatus.ACCEPTED, msg, data);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response(HttpStatus status, String msg, T data) {
        ResponseBody<T> body = new ResponseBody(msg, data);
        return new ResponseEntity(body, status);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response(HttpStatus status, String msg, T data, int total) {
        ResponseBody<T> body = new ResponseBody(msg, data, total);
        return new ResponseEntity(body, status);
    }

    public static <T> ResponseEntity<ResponseBody<T>> response(HttpStatus status, boolean success, String msg, T data) {
        ResponseBody<T> body = new ResponseBody(success, msg, data);
        return new ResponseEntity(body, status);
    }
}