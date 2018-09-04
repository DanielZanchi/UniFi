function If = trapcomp(n, a, b, fun)
% function If = trapcomp(n , a, b, fun)
% Calcola l'integrale della funzione nell'intervallo a b,
% utilizzando la formula dei trapezi composita.
% fun funzione integranda
% If valore approssimato dell'integrale definito della funzione
If = 0;
h = (b-a) / n;
for i = 1:n-1
    If = If + fun(a + i*h);
end
If = (h/2) * (2*If + fun(a) + fun(b));
end

