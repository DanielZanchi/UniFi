function If = simpcomp(n, a, b, fun)
% function If = trapcomp(n , a, b, fun)
% Calcola l'integrale della funzione nell'intervallo a b,
% utilizzando la formula di Simpson composita
% fun funzione integranda
% If valore approssimato dell'integrale definito della funzione
If = fun(a) - fun(b);
h = (b-a) / n;
for i = 1:n/2
    If = If + 4*fun(a+(2*i-1)*h) + 2*fun((a+2*i*h));
end
If = If*(h/3);
end
