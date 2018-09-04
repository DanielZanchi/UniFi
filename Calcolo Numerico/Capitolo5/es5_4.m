function If = simpad(a, b, fun, tol)
% function If = simpad(a ,b ,fun, tol)
% Calcola l'integrale della funzione nell'intervallo a b utilizzando la
% formula di simpson adattiva
% fun funzione integranda
% If approssimazione dell'integrale definito della funzione
h = (b-a) / 6;
m = (a+b) / 2;
m1 = (a+m) / 2;
m2 = (m+b) / 2;

If1 = h*(feval(fun, a) + 4*feval(fun, m) + feval(fun, b));
If = If1/2 + h*(2*feval(fun, m1) + 2*feval(fun, m2) - feval(fun, m));

err = abs(If-If1) / 15;

if err>tol
    iSx = simpad(a, m, fun, tol/2);
    iDx = simpad(m, b, fun, tol/2);
    If = iSx+iDx;
end
end