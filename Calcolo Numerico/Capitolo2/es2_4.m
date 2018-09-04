alpha = 5;
x0 = 5;

Tabella = cell2table(cell(0,3));
Tabella.Properties.VariableNames = {'i' 'SQRT_a' 'err'};
[Tabella, sqrt_a] = SQRT_Newton(alpha, x0, 100, 10^-15, Tabella);

function [T, sqrt_a] = SQRT_Newton(alpha, x0, itmax, tolx, T);
    sqrt_a = (x0 + alpha/x0) / 2;
    i = 1;
    row = {i, sqrt_a, abs( sqrt(alpha) - sqrt_a )};
    T = [T; row];

    while (i < itmax) && (abs(sqrt_a-x0) > tolx)
        x0 = sqrt_a;
        i = i+1;
        sqrt_a = (x0 + alpha/x0) / 2;
        row = {i, sqrt_a, abs( sqrt(alpha) - sqrt_a )};
        T = [T; row];
    end
end