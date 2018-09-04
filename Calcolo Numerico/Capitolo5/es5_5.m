% dati
If = 10^(-6);
fun = @(x) exp((-10^6)*x);
tol = 10^(-9);

[If_appr, nval] = trapcomp_val(10^7, 0, 1, fun);
print_res('Trapezi Composita', nval, abs(If-If_appr))

[If_appr, nval] = simpcomp_val(2*10^6, 0, 1, fun);
print_res('Simpson Composita', nval, abs(If-If_appr))

[If_appr, nval] = trapad_val(0, 1, fun, tol);
print_res('Trapezi Adattativa', nval, abs(If-If_appr))

[If_appr, nval] = simpad_val(0, 1, fun, tol);
print_res('Simpson Adattativa', nval, abs(If-If_appr))

% servono sempre n+1 valutazioni
function [If, nval] = trapcomp_val(n, a, b, fun)
    If = 0;
    nval = 0;
    h = (b-a) / n;
    for i = 1:n-1
        If = If + fun(a + i*h);
        nval = nval + 1;
    end
    If = (h/2) * (2*If + fun(a) + fun(b));
    nval = nval + 2;
end

% servono sempre n+2 valutazioni
function [If, nval] = simpcomp_val(n, a, b, fun)
    If = fun(a) - fun(b);
    nval = 2;
    h = (b-a) / n;
    for i = 1:n/2
        If = If + 4*fun(a+(2*i-1)*h) + 2*fun((a+2*i*h));
        nval = nval + 2;
    end
    If = If*(h/3);
end

% tre valutazioni di fun ad ogni call
function [If, neval] = trapad_val(a, b, fun, tol)
    h = (b-a)/2;
    m = (b+a)/2;

    If1 = h*(feval(fun, a) + feval(fun, b));
    If = If1/2 + h*feval(fun, m);
    neval = 3;

    err = abs(If - If1) / 3;

    if err > tol
        [iSx, nSx] = trapad_val(a, m, fun, tol/2);
        [iDx, nDx] = trapad_val(m, b, fun, tol/2);

        If = iSx + iDx;
        neval = neval + nSx + nDx;
    end
end


% sei valutazioni di fun ad ogni call
function [If, neval] = simpad_val(a, b, fun, tol)
    h = (b-a) / 6;
    m = (a+b) / 2;
    m1 = (a+m) / 2;
    m2 = (m+b) / 2;

    If1 = h*(feval(fun, a) + 4*feval(fun, m) + feval(fun, b));
    If = If1/2 + h*(2*feval(fun, m1) + 2*feval(fun, m2) - feval(fun, m));

    neval = 6;
    err = abs(If-If1) / 15;

    if err > tol
        [iSx, nSx] = simpad_val(a, m, fun, tol/2);
        [iDx, nDx] = simpad_val(m, b, fun, tol/2);

        If = iSx+iDx;
        neval = neval + nSx + nDx;
    end
end

% function per stampare il risultato
function print_res(fun_name, nval, err)
    disp(strcat(fun_name, ' - valutazioni f:'))
    disp(nval)
    disp(strcat(fun_name, ' - errore:'))
    disp(err)
end
