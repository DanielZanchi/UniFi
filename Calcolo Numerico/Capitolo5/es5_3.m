function If = trapad(a, b, fun, tol)
% function If = trapad(a, b, fun, tol)
% Calcola ricorsivamente l'integrale della funzione nell'intervallo a b
% utilizzando la formula dei trapezi adattiva.
% fun funzione integranda
% if approssimazione dell'integrale definito della funzione
h = (b-a)/2;
m = (b+a)/2;

If1 = h*(feval(fun, a) + feval(fun, b));
If = If1/2 + h*feval(fun, m);

err = abs(If - If1)/3;

if err > tol
    iSx = trapad(a, m, fun, tol/2);
    iDx = trapad(m, b, fun, tol/2);
    
    If = iSx + iDx;
end
end

