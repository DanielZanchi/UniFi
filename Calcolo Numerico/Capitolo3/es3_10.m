function [x] = sistemaNonLineare(F, J, b, imax, tolx)
% [x] = sistemaNonLineare(F, J, imax, tolx)
% Applica il metodo di Newton per un sistema non lineare.
% F sistema non lineare
% J matrice Jacobiana di F
% b vettore dei termini noti
% imax numero massimo di iterazioni
% tolx tolleranza
% [x] soluzione
i = 0;
xold = 0;
x = b;
while (i < imax) && (norm(x-xold) > tolx)
    i = i+1;
    xold = x;
    [A, p] = fatt_LUpivot(feval(J,x));
    x = x+sistema_LUpivot(A, p, -feval(F,x));
end
end